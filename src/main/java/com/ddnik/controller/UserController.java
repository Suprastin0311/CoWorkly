package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.db.Service;
import com.ddnik.db.dto.*;
import com.ddnik.model.*;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
        menu.addItem("Просмотреть свои брони", this::view);
        menu.addItem("Отменить бронирование", this::cancelBooking);
        menu.addItem("Выгрузить список броней в файл", () -> report(selectBookingsList()));

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

        Optional<UsersDto> currentUser = service.getUserByEmail(user.email());
        if (currentUser.isEmpty()) {
            Out.printlnRed("Для бронирования рабочего пространства необходимо авторизоваться.");
            return;
        }

        Optional<Long> newBookingId = service.createBooking(currentUser.get().id(), workspace.get(), filters.get());

        if (newBookingId.isPresent()) Out.printlnGreen("Бронь создана успешно!");
        else Out.printlnRed("Не удалось забронировать рабочее пространство.");
        ConsoleReader.waitInput();
    }

    /**
     * Отменить бронирование.
     */
    private void cancelBooking() throws SQLException {
        Optional<BookingDto> booking = selectBooking(selectBookingsList());
        if (booking.isEmpty()) {
            Out.printlnYellow("Не удалось выбрать бронирование.");
            ConsoleReader.waitInput();
            return;
        }

        Optional<Boolean> result = service.setBookingCancelled(booking.get().id());
        if (result.isPresent()) {
            if (result.get()) Out.printlnGreen("Удалось отменить бронирование.");
            else Out.printlnRed("Не удалось отменить бронирование.");
        }
        else Out.printlnRed("Не удалось выполнить операцию.");
        ConsoleReader.waitInput();
    }

    /**
     * Меню просмотра бронирований текущего пользователя.
     */
    private void view() {
        ConsoleMenu menu = new ConsoleMenu("Просмотреть бронирования.");
        menu.addItem("Все", () -> {
            new ItemsListMenu<>(selectAllBookings(), "Выбранные бронирования",
                    BookingDto.getMenuTableHeader()).display();
            ConsoleReader.waitInput();
        });
        menu.addItem("По статусу", () -> {
            new ItemsListMenu<>(selectBookingsByStatus(), "Выбранные бронирования",
                    BookingDto.getMenuTableHeader()).display();
            ConsoleReader.waitInput();
        });
        menu.addItem("По рабочему пространству", () -> {
            new ItemsListMenu<>(selectBookingsByWorkspace(), "Выбранные бронирования",
                    BookingDto.getMenuTableHeader()).display();
            ConsoleReader.waitInput();
        });
        menu.addItem("По датам", () -> {
            new ItemsListMenu<>(selectBookingsByCreatedAt(), "Выбранные бронирования",
                    BookingDto.getMenuTableHeader()).display();
            ConsoleReader.waitInput();
        });

        logger.info("Пользователь перешёл в меню просмотра бронирований.");
        menu.start();
    }

    /**
     * Меню выбора бронирования из списка.
     * @param bookings список бронирований.
     * @return выбранное бронирование.
     */
    private Optional<BookingDto> selectBooking(List<BookingDto> bookings) {
        return new ItemsListMenu<>(bookings,
                "Выберите бронирование",
                BookingDto.getMenuTableHeader()).start();
    }

    private List<BookingDto> selectBookingsList() {
        AtomicReference<List<BookingDto>> result = new AtomicReference<>();

        ConsoleMenu menu = new ConsoleMenu("Выберите параметр поиска бронирования.");
        menu.addItem("Все", () -> {
            result.set(selectAllBookings());
            menu.close();
        });
        menu.addItem("По статусу", () -> {
            result.set(selectBookingsByWorkspace());
            menu.close();
        });
        menu.addItem("По рабочему пространству", () -> {
            result.set(selectBookingsByCreatedAt());
            menu.close();
        });
        menu.addItem("По датам", () -> {
            result.set(selectBookingsByStatus());
            menu.close();
        });

        logger.info("Пользователь перешёл в меню выбора бронирования.");
        menu.start();

        return result.get();
    }

    /**
     * Просмотреть все бронирования.
     * @return список бронирований.
     */
    private List<BookingDto> selectAllBookings() throws SQLException {
        return service.getBookingsByUserId(user.id());
    }

    /**
     * Просмотреть бронирования с фильтром по статусу.
     * @return список бронирований.
     */
    private List<BookingDto> selectBookingsByStatus() throws SQLException {
        Optional<BookingStatusesDto> status = new ItemsListMenu<>(
                service.getBookingStatuses(),
                "Выберите статус",
                BookingStatusesDto.getMenuTableHeader()).start();
        if (status.isEmpty()) new ArrayList<>();

        return service.getBookingsByStatus(user.id(), status.get());
    }

    /**
     * Просмотреть бронирования с фильтром по рабочему пространству.
     * @return список бронирований.
     */
    private List<BookingDto> selectBookingsByWorkspace() throws SQLException {
        Optional<WorkspaceDto> workspace = new ItemsListMenu<>(
                service.getWorkspaces(),
                "Выберите рабочее пространство",
                WorkspaceDto.getMenuTableHeader()).start();
        if (workspace.isEmpty()) return new ArrayList<>();

        return service.getBookingsByWorkspaceId(user.id(), workspace.get());
    }

    /**
     * Получить бронирования с фильтром по датам
     * @return список бронирований.
     */
    private List<BookingDto> selectBookingsByCreatedAt() throws SQLException {
        Optional<Date> min = ConsoleReader.readDate("Введите минимальную дату");
        if (min.isEmpty()) return new ArrayList<>();

        Optional<Date> max = ConsoleReader.readDate("Введите максимальную дату");
        if (max.isEmpty()) return new ArrayList<>();

        return service.getBookingsByCreatedAt(user.id(), min.get(), max.get());
    }

    /**
     * Выгрузить список своих броней в файл формата CSV.
     */
    private void report(List<BookingDto> bookings) {
        if (bookings.isEmpty()) return;

        try {
            File csvFile = new File(String.format("bookings_report_%tF_%<tk-%<tM.csv", LocalDateTime.now()));
            CSVWriter writer = new CSVWriter(
                    new FileWriter(csvFile, false),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            writer.writeNext(new String[]{"Тип", "Название", "Начало", "Окончание", "Email регистратора", "Количество человек", "Статус", "Сумма", "Дата бронирования"});

            for(BookingDto booking : bookings) writer.writeNext(booking.toCSVRow());

            writer.close();

            Out.printlnCyan("Файл создан по пути: " + csvFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            Out.printlnRed("Ошибка создания CSV-файла.");
        } finally {
            ConsoleReader.waitInput();
        }
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
}
