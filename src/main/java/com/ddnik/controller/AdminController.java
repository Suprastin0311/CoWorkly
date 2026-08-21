package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.db.Service;
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
            menu.addItem("Скрытие", this::changeVisibility);
            menu.addItem("Создание", this::create);
            menu.addItem("Удаление", this::delete);

            logger.info("Администратор перешёл в меню управления рабочими пространствами.");
            menu.start();
        }

        private void changeVisibility() {
            Optional<WorkspaceDto> workspace = select();
            System.out.println("Деактивация рабочего пространства");
        }

        private void edit() {
            Optional<WorkspaceDto> workspace = select();
            System.out.println("Редактирование рабочего пространства");
        }

        private void create() {
            System.out.println("Создание рабочего пространства");
        }

        private void delete() {
            Optional<WorkspaceDto> workspace = select();
            System.out.println("Удаление рабочего пространства" + workspace);
        }

        private Optional<WorkspaceDto> select() {
            AtomicReference<WorkspaceDto> result = new AtomicReference<>();

            ConsoleMenu menu = new ConsoleMenu("Выберите параметр поиска рабочего пространства: ");
            menu.addItem("Тип", () -> {
                WorkspaceDto w = getWorkspace(selectByType());
                if (w != null) {
                    result.set(w);
                    menu.close();
                }
            });
            menu.addItem("Название", () -> {
                WorkspaceDto w = getWorkspace(selectByName());
                if (w != null) {
                    result.set(w);
                    menu.close();
                }
            });
            menu.addItem("Вместимость", () -> {
                WorkspaceDto w = getWorkspace(selectByCapacity());
                if (w != null) {
                    result.set(w);
                    menu.close();
                }
            });
            menu.addItem("Часовая стоимость", () -> {
                WorkspaceDto w = getWorkspace(selectByHourlyRate());
                if (w != null) {
                    result.set(w);
                    menu.close();
                }
            });
            menu.addItem("Статус", () -> {
                WorkspaceDto w = getWorkspace(selectByStatus());
                if (w != null) {
                    result.set(w);
                    menu.close();
                }
            });

            logger.info("Администратор перешёл в меню выбора рабочих пространств.");
            menu.start();

            return Optional.ofNullable(result.get());
        }

        private WorkspaceDto getWorkspace(List<WorkspaceDto> workspaces) {
            Optional<WorkspaceDto> workspace = new ItemsListMenu<>(workspaces, "Выберите рабочее пространство", WorkspaceDto.getMenuTableHeader()).start();
            if (workspace.isPresent()) {
                logger.debug("Получено рабочее пространство: {}", workspace.get());
                return workspace.get();
            } else {
                logger.debug("Не удалось получить рабочее пространство.");
                return null;
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
            System.out.print("Введите название рабочего пространства: ");
            String name = ConsoleReader.readString();

            if (name.isEmpty()) {
                System.out.println("Была введена пустая строка.");
                return new ArrayList<>();
            } else {
                return service.getWorkspacesByName(name);
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
         *
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
            ConsoleMenu menu = new ConsoleMenu("Просмотр броней с фильтрацией.");
            menu.addItem("Без фильтра", this::viwAll);
            menu.addItem("По пользователю", this::viewByUser);
            menu.addItem("По рабочему пространству", this::viewByWorkspace);
            menu.addItem("По дате", this::viewByDate);
            menu.addItem("По статусу", this::viewByStatus);

            logger.info("Администратор перешёл в меню управления бронированием.");
            menu.start();
        }

        /**
         * Посмотреть все брони.
         */
        private void viwAll() {
            System.out.println("Все брони");
        }

        /**
         * Посмотреть брони с фильтром по пользователю.
         */
        private void viewByUser() {
            System.out.println("Фильтр по пользователю");
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
