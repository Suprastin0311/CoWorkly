package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.db.Service;
import com.ddnik.db.dto.BookingDto;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.dto.WorkspaceTypesDto;
import com.ddnik.db.entity.*;
import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Управляет консольным меню администратора.
 */
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    /**
     * Данные авторизованного пользователя
     */
    private final AuthorizedUser admin;
    private final Service service;

    public AdminController(AuthorizedUser admin) {
        this.service = new Service();
        this.admin = admin;
    }

    public void start() {
        ConsoleMenu menu = new ConsoleMenu("Вы вошли как Администратор");
        menu.addItem("Пользователи", this::users);
        menu.addItem("Рабочие пространства", this::workspaces);
        menu.addItem("Брони", this::bookings);

        logger.info("Администратор перешёл в меню.");
        menu.start();
    }

    /**
     * Поиск пользователя по данным учётной записи.
     * @return модель данных пользователя
     */
    private Users findUser() throws Exception {
        ConsoleReader.cls();
        Scanner sc = new Scanner(System.in);

        do {
            ConsoleReader.printMenu("findUser");

            int choice = sc.nextInt();
            switch (choice) { // user = switch() -> yield
                case 1: // Email
                    ConsoleReader.cls();
                    boolean isEmailValid;
                    String email;
                    do {
                        System.out.print("Введите email (пример: exmaple@some.domen): ");
                        email = sc.next().trim();
                        isEmailValid = ConsoleReader.validateEmail(email);
                        if (isEmailValid) {
                            break;
                            // return User
                        }
                        System.out.println("\nОшибка. Убедитесь, что вводимый email удовлетворяет маске.");
                    } while (!isEmailValid);
                    break;
                case 2: // ФИО
                    ConsoleReader.cls();
                    System.out.print("Введите ФИО: ");
                    String name = sc.next().trim();

                    // return User;
                    break;
                case 3: // Роль
                    ConsoleReader.cls();
                    ConsoleReader.printMenu("findUserByRole");

                    int roleChoice = sc.nextInt();
                    switch (roleChoice) {
                        case 1:
                            System.out.println("Тут будут выбранные пользователи.");
                            //TODO вывод админов
                            //int usersCount =
//                                do {
//                                    TODO выбор админа
//                                    userChoice =
//                                    if (userChoice < 0 && userChoice > usersCount) {
//                                        System.out.println("В списке нет пользователя с указанным номером!");
//                                    }
//                                    else {
//                                        break;
//                                    }
//                                } while (true);
                            break;
                        case 2:
                            System.out.println("Тут будут выбранные пользователи.");
//                                TODO вывод пользователей
//                                do {
//                                    TODO выбор пользователя
//                                    userChoice =
//                                    if (userChoice < 0 && userChoice > usersCount) {
//                                        System.out.println("В списке нет пользователя с указанным номером!");
//                                    }
//                                    else {
//                                        break;
//                                    }
//                                } while (true);
                            break;
                        default: // Вернуться назад
                            ConsoleReader.cls();
                            break;
                    }

                    break;
                case 4: // Назад
                    throw new Exception("Отмена поиска.");
            }
        } while (true);
    }

    /**
     * Запуск меню работы с пользователями.
     */
    private void users() {
        new UsersController().start();
    }

    /**
     * Запуск меню работы с рабочими пространствами.
     */
    private void workspaces() {
        new WorkspaceController().start();
    }

    /**
     * Запуск меню работы с бронями.
     */
    private void bookings() {
        new BookingController().start();
    }

    /**
     * Контроллер меню работы с учётными записями пользователей.
     */
    class UsersController {

        public void start() {
            ConsoleMenu menu = new ConsoleMenu("Управление пользователями");
            menu.addItem("Посмотреть всех пользователей", this::viewAll);
            menu.addItem("Заблокировать / разблокировать пользователя", this::edit);

            logger.info("Администратор перешёл в меню управления пользователями.");
            menu.start();
        }

        /**
         * Вывод информации о пользователях.
         */
        private void viewAll() {
            System.out.println("Тут будет вывод всех пользователей.");
        }

        /**
         * Редактирование пользователя: заблокировать или разблокировать.
         */
        private void edit() {
            System.out.println("Тут будет меню редактирования доступа пользователей к программе.");
        }

        private Optional<UsersDto> select() {
            AtomicReference<UsersDto> result = new AtomicReference<>();

            ConsoleMenu menu = new ConsoleMenu("Выберите параметр поиска пользователя: ");
            menu.addItem("Email", () -> selectByEmail().ifPresent(u ->  {
                result.set(u);
                menu.close();
            }));
            menu.addItem("Имя", () -> getUser(selectByName()).ifPresent(u ->  {
                result.set(u);
                menu.close();
            }));
            menu.addItem("Роль", () -> getUser(selectByRole()).ifPresent(u ->  {
                result.set(u);
                menu.close();
            }));

            logger.info("Администратор перешёл в меню выбора пользователей.");
            menu.start();

            return Optional.ofNullable(result.get());
        }

        private Optional<UsersDto> getUser(List<UsersDto> users) throws SQLException, SecurityException, ConsoleUserInputException {
            return Optional.empty();
        }

        private Optional<UsersDto> selectByEmail() throws SQLException, SecurityException, ConsoleUserInputException {
            ConsoleReader.cls();
            System.out.print("Введите email: ");
            String email = ConsoleReader.readString();

            if (ConsoleReader.validateEmail(email)) {
                System.out.println("Email не соответствует шаблону example@mail.domen");
                return Optional.empty();
            }

            return service.getUserByEmail(email);
        }

        private List<UsersDto> selectByRole() {
            return new ArrayList<>();
        }

        private List<UsersDto> selectByName() {
            return new ArrayList<>();
        }
    }

    /**
     * Контроллер меню работы с рабочими пространствами.
     */
    class WorkspaceController {

        public void start() {
            ConsoleMenu menu = new ConsoleMenu("Управление рабочими пространствами");
            menu.addItem("Просмотр всех", this::viwAll);
            menu.addItem("Просмотр выбранного", this::view);
            menu.addItem("Редактирование", this::edit);
            menu.addItem("Создание", this::create);
            menu.addItem("Удаление", this::delete);

            logger.info("Администратор перешёл в меню управления рабочими пространствами.");
            menu.start();
        }
        
        /**
         * Редактирование рабочего пространства.
         */
        private void edit() throws SecurityException {
            Optional<WorkspaceDto> workspace = select();

            if (workspace.isPresent()) {
                ConsoleMenu menu = new ConsoleMenu("Выберите редактируемый параметр рабочего пространства.");
                menu.addItem("Тип", () -> editType(workspace.get()));
                menu.addItem("Название", () -> editName(workspace.get()));
                menu.addItem("Вместимость", () -> editCapacity(workspace.get()));
                menu.addItem("Часовая стоимость", () -> editHourlyRate(workspace.get()));
                menu.addItem("Переключить статус", () -> changeVisibility(workspace.get()));

                logger.info("Администратор перешёл в меню редактирования рабочего пространства.");
                menu.start();
            }
            else {
                System.out.println("Не удалось выбрать рабочее пространство.");
            }
        }

        private void editType(WorkspaceDto workspace) throws SQLException, SecurityException, ConsoleUserInputException {
            if (!service.getBookingsByWorkspaceId(workspace).isEmpty()) {
                System.out.println("Выбранное рабочее пространство забронировано в данный момент - редактирование недоступно.");
                return;
            }

            List<WorkspaceTypesDto> typeList = service.getWorkspaceTypes();
            while (true) {
                Optional<WorkspaceTypesDto> type = new ItemsListMenu<>(typeList,
                        "Выберите новый тип рабочего пространства.",
                        WorkspaceTypesDto.getMenuTableHeader()).start();
                if (type.isPresent()) {
                    if (type.get().id() == workspace.type().id()) {
                        System.out.println("Выбранный тип совпадает с текущим.");
                    }
                    else {
                        int capacity = workspace.capacity();
                        if (workspace.capacity() < type.get().minParticipantsCount()
                                || workspace.capacity() > type.get().maxParticipantsCount()) {
                            System.out.println("Текущее значение вместимости нарушает ограничения нового типа.");
                            while (true) {
                                System.out.printf("Введите новое значение вместимости [%d, %d]: ",
                                        type.get().minParticipantsCount(),
                                        type.get().maxParticipantsCount());
                                capacity = ConsoleReader.readPositiveInt();
                                if (capacity < type.get().minParticipantsCount()
                                        || capacity > type.get().maxParticipantsCount())
                                    System.out.printf("Введённое значение вместимости нарушает ограничения типа [%d, %d]\n",
                                            type.get().minParticipantsCount(),
                                            type.get().maxParticipantsCount());
                                else break;
                            }
                        }

                        Workspaces editedWorkspace = new Workspaces(
                                workspace.id(),
                                type.get().id(),
                                workspace.name(),
                                capacity,
                                workspace.hourlyRate(),
                                workspace.is_active());

                        update(editedWorkspace);
                        return;
                    }
                }
                else {
                    System.out.println("Не удалось выбрать тип.");
                    logger.debug("Не удалось выбрать тип рабочего пространства.");
                    return;
                }
            }
        }

        private void editName(WorkspaceDto workspace) throws SQLException, SecurityException, ConsoleUserInputException {
            while (true) {
                System.out.print("Введите новое название: ");
                String name = ConsoleReader.readString();

                if (workspace.name().equals(name)) System.out.println("Введённое название совпадает с текущим.");
                else {
                    Workspaces editedWorkspace = new Workspaces(
                            workspace.id(),
                            workspace.type().id(),
                            name,
                            workspace.capacity(),
                            workspace.hourlyRate(),
                            workspace.is_active());

                    update(editedWorkspace);
                    return;
                }
            }
        }

        private void editCapacity(WorkspaceDto workspace) throws SQLException, SecurityException, ConsoleUserInputException {
            while (true) {
                System.out.print("Введите новое значение вместимости: ");
                int capacity = ConsoleReader.readPositiveInt();
                if (workspace.capacity() == capacity) System.out.println("Введённое значение совпадает с текущим.");
                else if (capacity < workspace.type().minParticipantsCount()
                        || capacity > workspace.type().maxParticipantsCount())
                    System.out.printf("Введённое значение вместимости нарушает ограничения типа [%d, %d]\n",
                            workspace.type().minParticipantsCount(), workspace.type().maxParticipantsCount());
                else {
                    Workspaces editedWorkspace = new Workspaces(
                            workspace.id(),
                            workspace.type().id(),
                            workspace.name(),
                            capacity,
                            workspace.hourlyRate(),
                            workspace.is_active());

                    update(editedWorkspace);
                    return;
                }
            }
        }

        private void editHourlyRate(WorkspaceDto workspace) throws SQLException, SecurityException, ConsoleUserInputException {
            while (true) {
                System.out.print("Введите новое значение часовой стоимости: ");
                BigDecimal hourlyRate = BigDecimal.valueOf(ConsoleReader.readDouble());

                if (workspace.hourlyRate().compareTo(hourlyRate) == 0)
                    System.out.println("Введённое значение совпадает с текущим.");
                else if (hourlyRate.compareTo(BigDecimal.ZERO) < 0)
                    System.out.println("Часовая стоимость не может быть отрицательным числом.");
                else {
                    Workspaces editedWorkspace = new Workspaces(
                            workspace.id(),
                            workspace.type().id(),
                            workspace.name(),
                            workspace.capacity(),
                            hourlyRate,
                            workspace.is_active());

                    update(editedWorkspace);
                    return;
                }
            }
        }

        private void changeVisibility(WorkspaceDto workspace) throws SQLException, SecurityException {
            Optional<Boolean> result = service.toggleWorkspaceActiveStatus(workspace.id());
            if (result.isPresent())
                if (result.get()) System.out.println("Статус успешно изменён.");
                else System.out.println("Не удалось изменить статус рабочего пространства.");
        }

        private void update(Workspaces workspace) throws SQLException, SecurityException {
            Optional<Boolean> updateStatus = service.updateWorkspace(workspace);
            if (updateStatus.isPresent()) {
                if (updateStatus.get()) {
                    System.out.println("Рабочее пространство успешно обновлено.");
                    logger.debug("Обновлено рабочее пространство по id {}", workspace.id());
                }
                else System.out.println("Не удалось обновить рабочее пространство.");
                logger.debug("Не удалось обновить рабочее пространство по id {}", workspace.id());
                ConsoleReader.waitInput();
            }
            else {
                System.out.println("База данных не вернула ответ.");
                logger.debug("База данных не вернула ответ.");
            }
        }

        private void create() throws SQLException, SecurityException, ConsoleUserInputException {
            List<WorkspaceDto> workspaces = service.getWorkspacesByName(""); // получить все рабочие пространства
            // имена существующих рабочих пространств
            List<String> names = workspaces.stream()
                                        .map(WorkspaceDto::name)
                                        .filter(name -> name != null && !name.isBlank()).toList();
            System.out.println("Создание рабочего пространства.");
            Optional<WorkspaceTypesDto> type;
            while (true) {
                type = selectWorkspaceType();
                if (type.isEmpty()) System.out.println("Не удалось выбрать тип.");
                else break;
            }

            String name;
            while (true) {
                System.out.print("Название: ");
                name = ConsoleReader.readString();
                if (names.contains(name)) System.out.println("Данное название уже используется.");
                else break;
            }

            int capacity;
            while (true) {
                System.out.printf("Вместимость [%d, %d]: ", type.get().minParticipantsCount(), type.get().maxParticipantsCount());
                capacity = ConsoleReader.readPositiveInt();
                if (capacity < type.get().minParticipantsCount() || capacity > type.get().maxParticipantsCount())
                    System.out.printf("Указанная вместимость вне допустимых значений [%d, %d].\n",
                            type.get().minParticipantsCount(),
                            type.get().maxParticipantsCount());
                else break;
            }

            BigDecimal hourlyRate;
            while (true) {
                System.out.print("Часовая стоимость: ");
                hourlyRate = BigDecimal.valueOf(ConsoleReader.readDouble());
                if (hourlyRate.compareTo(BigDecimal.ZERO) < 0)
                    System.out.println("Часовая стоимость не может быть отрицательным числом.");
                else break;
            }

            System.out.println("Доступно для брони сразу после создания?");
            System.out.println("1 - да");
            System.out.println("2 - нет");
            boolean isActive = ConsoleReader.chooseMenuItem(1, 2) == 1;

            Workspaces newWorkspace = new Workspaces(
                type.get().id(),
                name,
                capacity,
                hourlyRate,
                isActive
            );

            Optional<Long> newWorkspaceId = service.insertWorkspace(newWorkspace);
            if (newWorkspaceId.isPresent()) System.out.println("Рабочее пространство создано успешно.");
            ConsoleReader.waitInput();
        }

        private void delete() throws SQLException, SecurityException, ConsoleUserInputException {
            System.out.println("Удаление рабочего пространства.");
            Optional<WorkspaceDto> workspace = select();
            System.out.println("Рабочее пространство будет БЕЗВОЗВРАТНО удалено.");
            System.out.println(WorkspaceDto.getMenuTableHeader());
            System.out.println("1 | " + workspace.get().toMenuTableRow());
            System.out.println("Подтвердить удаление? ");
            System.out.println("1 - да");
            System.out.println("2 - нет");
            if (ConsoleReader.chooseMenuItem(1, 2) == 1) {
                Optional<Boolean> result = service.deleteWorkspace(workspace.get().id());
                if (result.isPresent()) {
                    if (result.get()) System.out.println("Рабочее пространство успешно безвозвратно удалено.");
                    else System.out.println("Не удалось удалить рабочее пространство.");
                }
                else System.out.println("Не удалось выполнить операцию.");
                ConsoleReader.waitInput();
            }
        }

        private Optional<WorkspaceDto> select() {
            AtomicReference<WorkspaceDto> result = new AtomicReference<>();

            ConsoleMenu menu = new ConsoleMenu("Выберите параметр поиска рабочего пространства: ");
            menu.addItem("Тип", () -> getWorkspace(selectByType()).ifPresent(w -> {
                result.set(w);
                menu.close();
            }));
            menu.addItem("Название", () -> getWorkspace(selectByName()).ifPresent(w -> {
                result.set(w);
                menu.close();
            }));
            menu.addItem("Вместимость", () -> getWorkspace(selectByCapacity()).ifPresent(w -> {
                result.set(w);
                menu.close();
            }));
            menu.addItem("Часовая стоимость", () -> getWorkspace(selectByHourlyRate()).ifPresent(w -> {
                result.set(w);
                menu.close();
            }));
            menu.addItem("Статус", () -> getWorkspace(selectByStatus()).ifPresent(w -> {
                result.set(w);
                menu.close();
            }));

            logger.info("Администратор перешёл в меню выбора рабочих пространств.");
            menu.start();

            return Optional.ofNullable(result.get());
        }

        private Optional<WorkspaceDto> getWorkspace(List<WorkspaceDto> workspaces) {
            Optional<WorkspaceDto> workspace = new ItemsListMenu<>(workspaces, "Выберите рабочее пространство",
                    WorkspaceDto.getMenuTableHeader()).start();
            if (workspace.isPresent()) {
                logger.debug("Получено рабочее пространство: {}", workspace.get());
                return workspace;
            } else {
                logger.debug("Не удалось получить рабочее пространство.");
                return Optional.empty();
            }
        }

        private List<WorkspaceDto> selectByType() throws SQLException, SecurityException {
            ConsoleReader.cls();
            Optional<WorkspaceTypesDto> type = selectWorkspaceType();

            if (type.isPresent()) {
                ConsoleReader.cls();
                return service.getWorkspacesByType(type.get().id());
            } else {
                return new ArrayList<>();
            }
        }

        private List<WorkspaceDto> selectByName() throws SQLException, SecurityException, ConsoleUserInputException {
            ConsoleReader.cls();
            while (true) {
                System.out.print("Введите название рабочего пространства: ");
                String name = ConsoleReader.readString();

                if (name.isEmpty()) System.out.println("Была введена пустая строка.");
                else return service.getWorkspacesByName(name);
            }
        }

        private List<WorkspaceDto> selectByCapacity() throws SQLException, SecurityException, ConsoleUserInputException {
            ConsoleReader.cls();
            System.out.print("Введите вместимость рабочего пространства: ");
            int capacity = ConsoleReader.readPositiveInt();

            return service.getWorkspacesByCapacity(capacity);
        }

        private List<WorkspaceDto> selectByHourlyRate() throws SQLException, SecurityException, ConsoleUserInputException {
            ConsoleReader.cls();
            System.out.print("Введите минимальную часовую стоимость рабочего пространства: ");
            BigDecimal minRate = BigDecimal.valueOf(ConsoleReader.readDouble());
            System.out.print("Введите максимальную часовую стоимость рабочего пространства: ");
            BigDecimal maxRate = BigDecimal.valueOf(ConsoleReader.readDouble());

            return service.getWorkspacesByHourlyRate(minRate, maxRate);
        }

        private List<WorkspaceDto> selectByStatus() throws SQLException, SecurityException, ConsoleUserInputException {
            ConsoleReader.cls();
            System.out.println("Выберите статус рабочего пространства: ");
            System.out.println("1 - Активно");
            System.out.println("2 - Заблокировано");
            System.out.println("0 - Назад");
            int selectedItem = ConsoleReader.chooseMenuItem(0, 2);
            boolean status = true;
            switch (selectedItem) {
                case 0 -> {
                    return new ArrayList<>();
                }
                case 2 -> {
                    status = false;
                }
            }

            return service.getWorkspacesByStatus(status);
        }

        /**
         * Выбрать тип рабочего пространства из списка.
         * @return тип рабочего пространства.
         */
        private Optional<WorkspaceTypesDto> selectWorkspaceType() throws SQLException {
            Optional<WorkspaceTypesDto> type;
            ConsoleReader.cls();
            type = new ItemsListMenu<>(
                    service.getWorkspaceTypes(),
                    "Выберите тип рабочего пространства",
                    WorkspaceTypesDto.getMenuTableHeader()).start();

            return type;
        }

        /**
         * Просмотреть рабочие пространства.
         */
        private void viwAll() throws SQLException, SecurityException, ConsoleUserInputException {
            new ItemsListMenu<>(
                    service.getWorkspacesByName(""),
                    "Все рабочие пространства",
                    WorkspaceDto.getMenuTableHeader()
            ).display();
            ConsoleReader.waitInput();
        }

        /**
         * Посмотреть рабочие пространства с фильтром по атрибуту.
         */
        private void view() {
            ConsoleMenu menu = new ConsoleMenu("Выберите параметр поиска рабочего пространства: ");
            menu.addItem("Тип", () -> {
                new ItemsListMenu<>(selectByType(), "Найденные рабочие пространства", WorkspaceDto.getMenuTableHeader()).display();
                ConsoleReader.waitInput();
            });
            menu.addItem("Название", () -> {
                new ItemsListMenu<>(selectByName(), "Найденные рабочие пространства", WorkspaceDto.getMenuTableHeader()).display();
                ConsoleReader.waitInput();
            });
            menu.addItem("Вместимость", () -> {
                new ItemsListMenu<>(selectByCapacity(), "Найденные рабочие пространства", WorkspaceDto.getMenuTableHeader()).display();
                ConsoleReader.waitInput();
            });
            menu.addItem("Часовая стоимость", () -> {
                new ItemsListMenu<>(selectByHourlyRate(), "Найденные рабочие пространства", WorkspaceDto.getMenuTableHeader()).display();
                ConsoleReader.waitInput();
            });
            menu.addItem("Статус", () -> {
                new ItemsListMenu<>(selectByStatus(), "Найденные рабочие пространства", WorkspaceDto.getMenuTableHeader()).display();
                ConsoleReader.waitInput();
            });

            logger.info("Администратор перешёл в меню просмотра рабочих пространств.");
            menu.start();
        }
    }

    /**
     * Контроллер меню работы с бронями.
     */
    class BookingController {

        public void start() {
            ConsoleMenu menu = new ConsoleMenu("Просмотр бронирований.");
            menu.addItem("Все", this::viwAll);
            menu.addItem("По пользователю", this::viewByUser);
            menu.addItem("По рабочему пространству", this::viewByWorkspace);
            menu.addItem("По дате", this::viewByDate);
            menu.addItem("По статусу", this::viewByStatus);

            logger.info("Администратор перешёл в меню просмотра бронирований.");
            menu.start();
        }

        /**
         * Посмотреть все брони.
         */
        private void viwAll() throws SQLException, SecurityException {
            List<BookingDto> bookings = service.getBookings();
            new ItemsListMenu<>(bookings, "Все бронирования", BookingDto.getMenuTableHeader()).display();
            ConsoleReader.waitInput();
        }

        /**
         * Посмотреть брони с фильтром по пользователю.
         */
        private void viewByUser() {
            //List<BookingDto> bookings = service.getBookingsByUserId();
        }

        /**
         * Посмотреть брони с фильтром по рабочему пространству.
         */
        private void viewByWorkspace() {
            System.out.println("Фильтр по рабочему пространству");
        }

        /**
         * Посмотреть брони с фильртом по дате.
         */
        private void viewByDate() {
            System.out.println("Фильтр по дате");
        }

        /**
         * Посмотреть брони с фильтром по статусу.
         */
        private void viewByStatus() {
            System.out.println("Фильтр по статусу");
        }

        /**
         * Вывод списка броней в консоль.
         * @param bookings список броней
         */
        private void view(ArrayList<Bookings> bookings) {

        }

        /**
         * Выбрать статус брони из списка.
         * @return выбранный статус
         * @throws ConsoleUserInputException в случае ошибки ввода.
         */
        private BookingStatuses selectBookingStatus() throws ConsoleUserInputException {
            ConsoleReader.cls();
            do {

            } while (true);
        }
    }
}
