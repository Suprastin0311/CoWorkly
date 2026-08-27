package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.db.Service;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceAvailableDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.dto.WorkspaceTypesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Управляет консольным меню пользователя.
 */
public class UserController {

    private final Service service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private AuthorizedUser user;

    public UserController(AuthorizedUser user) {
        this.user = user;
        this.service = new Service();
    }

    public void start() {
        ConsoleMenu menu = new ConsoleMenu("Вы вошли как Пользователь");
        menu.addItem("Посмотреть свободные рабочие пространства", this::viewFreeWorkspaces);
        menu.addItem("Забронировать рабочее пространство", this::bookWorkspace);
        menu.addItem("Просмотреть свои брони", this::viewBookings);
        menu.addItem("Отменить бронирование", this::cancelBooking);
        menu.addItem("Выгрузить список броней в файл", this::report);

        logger.info("пользователь перешёл в меню.");
        menu.start();
    }

    /**
     * Просмотреть все свободные рабочие пространства.
     */
    private void viewFreeWorkspaces() throws SQLException {
        Optional<Filters> filters = getFilters();
        if (filters.isEmpty()) return;

        new ItemsListMenu<>(getAvailableWorkspaces(filters.get()),
                "Доступные рабочие пространства",
                WorkspaceAvailableDto.getMenuTableHeader()).display();
        ConsoleReader.waitInput();
    }

    /**
     * Выбрать список доступных рабочих пространств.
     * @return список доступных рабочих пространств.
     * @throws SQLException в случае ошибки с базой данных.
     */
    private List<WorkspaceAvailableDto> getAvailableWorkspaces(Filters filters) throws SQLException {
        return service.getWorkspacesAvailableForBooking(
                filters.startTime(),
                filters.endTime(),
                filters.type().id(),
                filters.participantsCount());
    }

    /**
     * Выбрать рабочее пространство из списка доступных.
     * @return рабочее пространство.
     * @throws SQLException в случае ошибки с базой данных.
     */
    private Optional<WorkspaceDto> selectWorkspace(Filters filters) throws SQLException {
        return selectAvailableWorkspace(filters).map(WorkspaceAvailableDto::toWorkspaceDto);
    }

    /**
     * Выбрать доступное рабочее пространство из списка.
     * @return доступное рабочее пространство.
     * @throws SQLException в случае ошибки с базой данных.
     */
    private Optional<WorkspaceAvailableDto> selectAvailableWorkspace(Filters filters) throws SQLException {
        return new ItemsListMenu<>(
                getAvailableWorkspaces(filters),
                "Выберите рабочее пространство из доступных",
                WorkspaceAvailableDto.getMenuTableHeader()).start();
    }

    /**
     * Составить фильтры для отбора доступных рабочих пространств с целью последующего бронирования.
     * @return объект с введёнными фильтрами
     */
    private Optional<Filters> getFilters() throws SQLException {
        ConsoleReader.cls();
        Optional<WorkspaceTypesDto> type = selectWorkspaceType();
        if (type.isEmpty()) return Optional.empty();

        Optional<Integer> participantsCount = ConsoleReader.readIntInRange("Укажите количество человек",
                type.get().minParticipantsCount(), type.get().maxParticipantsCount());
        if (participantsCount.isEmpty()) return Optional.empty();

        Optional<Date> date = ConsoleReader.readDate("Введите дату бронирования");
        if (date.isEmpty()) return Optional.empty();

        Optional<Time> startTime = ConsoleReader.readTime("Введите время начала брони");
        if (startTime.isEmpty()) return Optional.empty();

        boolean repeat = true;
        Optional<Time> endTime = startTime;
        while (repeat) {
            endTime = ConsoleReader.readTime("Введите время окончания брони");
            if (endTime.isEmpty()) return Optional.empty();
            if (endTime.get().before(startTime.get()))
                Out.printlnYellow("Дата окончания не может быть раньше даты начала.");
            else repeat = false;
        }

        return Optional.of(new Filters (
                type.get(),
                participantsCount.get(),
                Timestamp.valueOf(LocalDateTime.of(date.get().toLocalDate(), startTime.get().toLocalTime())),
                Timestamp.valueOf(LocalDateTime.of(date.get().toLocalDate(), endTime.get().toLocalTime()))
        ));
    }

    /**
     * Забронировать.
     */
    private void bookWorkspace() throws SQLException {
        Optional<Filters> filters = getFilters();
        if (filters.isEmpty()) return;

        Optional<WorkspaceDto> workspace = selectWorkspace(filters.get());
        if (workspace.isEmpty()) {
            Out.printlnRed("Не удалось выбрать рабочее пространство.");
            return;
        }

        Optional<UsersDto> currentUser = service.getUserByEmail(user.getEmail());
        if (currentUser.isEmpty()) {
            Out.printlnRed("Для бронирования рабочего пространства необходимо авторизоваться.");
            return;
        }

        Optional<Long> newBookingId = service.createBooking(currentUser.get().id(),
                workspace.get().id(),
                filters.get().startTime(),
                filters.get().endTime(),
                filters.get().participantsCount());

        if (newBookingId.isPresent()) Out.printlnGreen("Бронь создана успешно!");
        else Out.printlnRed("Не удалось забронировать рабочее пространство.");
        ConsoleReader.waitInput();
    }

    /**
     * Отменить бронирование.
     */
    private void cancelBooking() {

    }

    /**
     * Просмотреть свои брони.
     */
    private void viewBookings() {

    }

    /**
     * Выгрузить список своих броней в файл формата CSV.
     */
    private void report() {

    }

    /**
     * Выбрать тип рабочего пространства из списка.
     * @return тип рабочего пространства.
     */
    private Optional<WorkspaceTypesDto> selectWorkspaceType() throws SQLException {
        ConsoleReader.cls();
        return new ItemsListMenu<>(
                service.getWorkspaceTypes(),
                "Выберите тип рабочего пространства",
                WorkspaceTypesDto.getMenuTableHeader()).start();
    }

    /**
     * Хранит данные для фильтрации рабочих пространств.
     */
    record Filters(
            WorkspaceTypesDto type,
            int participantsCount,
            Timestamp startTime,
            Timestamp endTime
    ) {
        Filters {
            Objects.requireNonNull(type);
            Objects.requireNonNull(startTime);
            Objects.requireNonNull(endTime);
        }
    }
}
