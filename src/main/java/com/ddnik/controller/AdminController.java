package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.db.Service;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.entity.*;
import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

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

        /**
         * Просмотреть рабочие пространства.
         */
        private void viwAll() {
            System.out.println("Тут будут все рабочие пространства.");
            try {
                //TODO вывод рабочих пространств
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private void view() {
            Workspaces workspaces = findWorkspace();
            System.out.println("Тут будет выбранное рабочее пространство.");
        }

        private void changeVisibility() {
            Workspaces workspace = findWorkspace();
            System.out.println("Деактивация рабочего пространства");
        }

        private void edit() {
            Workspaces workspace = findWorkspace();
            System.out.println("Редактирование рабочего пространства");
        }

        private void create() {
            System.out.println("Создание рабочего пространства");
        }

        private void delete() {
            Workspaces workspace = findWorkspace();
            System.out.println("Удаление рабочего пространства");
        }

        /**
         * Поиск рабочего пространства по параметрам.
         */
        private Workspaces findWorkspace() {
            ConsoleReader.cls();
            ConsoleReader.printMenu("findWorkspace");

            do {
                try {
                    int choice = ConsoleReader.chooseMenuItem(6);

                    switch (choice) {
                        case 1: // тип
                            try {
                                ConsoleReader.cls();
                                ArrayList<WorkspaceTypes> workspaceTypes = service.getWorkspaceTypes();
                                if (!workspaceTypes.isEmpty()) {
                                    ConsoleMenu.showWorkspaceTypesDirectoryMenu(workspaceTypes);
                                }
                                else {
                                    System.out.println("Список рабочих пространств пуст.");
                                    break;
                                }

                                do {
                                    try {
                                        int selectType = ConsoleReader.chooseMenuItem(workspaceTypes.size());

                                        if (selectType == 0) {
                                            break;
                                        }

                                        // TODO найти и вывести рабочие пространства по типу
                                        System.out.print("Выберите рабочее пространство: ");

                                        int selectedWorkspace = ConsoleReader.chooseMenuItem(0); // TODO указать количество рабочих пространств
                                        // TODO return Workspace

                                    } catch (ConsoleUserInputException e) {
                                        System.out.println(e.getMessage());
                                        break;
                                    }
                                } while (true);
                            } catch (SQLException e) {
                                System.out.println("Не удалось получить список типов рабочих пространств - произошла ошибка на уровне базы данных.");
                            }
                            break;
                        case 2: // название
                            ConsoleReader.cls();
                            do {
                                try {
                                    System.out.print("Название рабочего пространства: ");
                                    String workspaceName = ConsoleReader.readString();

                                    // TODO найти и вывести рабочие пространства

                                    System.out.print("Выберите рабочее пространство: ");

                                    int selectedWorkspace = ConsoleReader.chooseMenuItem(0); // TODO указать количество рабочих пространств
                                    // TODO return Workspace
                                } catch (ConsoleUserInputException e) {
                                    System.out.println(e.getMessage());
                                    break;
                                }
                            } while (true);
                            break;
                        case 3: // вместимость
                            ConsoleReader.cls();
                            do {
                                try {
                                    System.out.print("Укажите вместимость: ");
                                    int capacity = ConsoleReader.readPositiveInt();

                                    // TODO найти и вывести рабочие пространства

                                    System.out.print("Выберите рабочее пространство: ");

                                    int selectedWorkspace = ConsoleReader.chooseMenuItem(0); // TODO указать количество рабочих пространств
                                    // TODO return Workspace
                                } catch (ConsoleUserInputException e) {
                                    System.out.println(e.getMessage());
                                    break;
                                }
                            } while (true);
                            break;
                        case 4: // стоимость
                            ConsoleReader.cls();
                            do {
                                try {
                                    System.out.print("Укажите часовую стоимость: ");
                                    double hourRate = ConsoleReader.readDouble();

                                    // TODO найти и вывести рабочие пространства

                                    System.out.print("Выберите рабочее пространство: ");

                                    int selectedWorkspace = ConsoleReader.chooseMenuItem(0); // TODO указать количество рабочих пространств
                                    // TODO return Workspace
                                } catch (ConsoleUserInputException e) {
                                    System.out.println(e.getMessage());
                                    break;
                                }
                            } while (true);
                            break;
                        case 5: // статус
                            ConsoleReader.cls();
                            do {
                                try {
                                    ConsoleReader.printMenu("findWorkspaceByStatus");
                                    int selectedStatus = ConsoleReader.chooseMenuItem(3);

                                    if (selectedStatus == 1) {
                                        // TODO указать статус в поиск
                                    }
                                    else if (selectedStatus == 2) {
                                        // TODO указать статус в поиск
                                    }
                                    else if (selectedStatus == 3) {
                                        break;
                                    }

                                    // TODO найти и вывести рабочие пространства

                                    System.out.print("Выберите рабочее пространство: ");

                                    int selectedWorkspace = ConsoleReader.chooseMenuItem(0); // TODO указать количество рабочих пространств
                                    // TODO return Workspace
                                } catch (ConsoleUserInputException e) {
                                    System.out.println(e.getMessage());
                                    break;
                                }
                            } while (true);
                            break;
                        case 6: // назад
                            // return
                            break;
                    }

                } catch (ConsoleUserInputException e) {
                    System.out.print(e.getMessage() + "\n\n>");
                }
            } while (true);
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
