package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.db.Service;
import com.ddnik.db.dto.WorkspaceDto;
import com.ddnik.db.dto.WorkspaceTypesDto;
import com.ddnik.db.entity.*;
import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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
            try {



            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private void changeVisibility() {
            WorkspaceDto workspace = findWorkspace();
            System.out.println("Деактивация рабочего пространства");
        }

        private void edit() {
            WorkspaceDto workspace = findWorkspace();
            System.out.println("Редактирование рабочего пространства");
        }

        private void create() {
            System.out.println("Создание рабочего пространства");
        }

        private void delete() {
            WorkspaceDto workspace = findWorkspace();
            System.out.println("Удаление рабочего пространства");
        }

        /**
         * Посмотреть рабочие пространства с фильтром по атрибуту.
         */
        private void view() {
            ConsoleMenu menu = new ConsoleMenu("Выберите параметр поиска рабочего пространства: ");
            menu.addItem("Тип", this::viewByType);
            menu.addItem("Название", this::viewByName);
            menu.addItem("Вместимость", this::viewByCapacity);
            menu.addItem("Часовая стоимость", this::viewByHourlyRate);
            menu.addItem("Статус", this::viewByStatus);

            logger.info("Администратор перешёл в меню просмотра рабочих пространств.");
            menu.start();
        }

        private void viewByType() {
            try {
                ItemsListMenu menu = new ItemsListMenu(
                        service.getWorkspaceTypes(),
                        "Выберите тип рабочего пространства",
                        "№ | Название | Минимум человек | Максимум человек");
                ConsoleReader.cls();
                Optional<WorkspaceTypesDto> selectedType = menu.start();

                if (selectedType.isPresent())
                    ArrayList<WorkspaceDto> workspaces = service.getWorkspacesByType(selectedType.get().toWorkspaceType());

            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
                logger.error(e.getLocalizedMessage(), e);
            }

        }

        private void viewByName() {

        }

        private void viewByCapacity() {

        }

        private void viewByHourlyRate() {

        }

        private void viewByStatus() {

        }



        /**
         * Фильтрация рабочего пространства по атрибутам.
         * @return отфильтрованный список рабочих пространств.
         */
        private ArrayList<WorkspaceDto> getFilteredWorkspaceList() {
            ArrayList<WorkspaceDto> workspaces;



            do {
                try {
                    ConsoleReader.printMenu("findWorkspace");
                    int choice = ConsoleReader.chooseMenuItem(1, 6);

                    switch (choice) {
                        case 1: // тип
                            try {

                                if (selectedType.isPresent()) {

                                }
                                else {
                                    break;
                                }
                            } catch (SQLException e) {
                                System.out.println("Не удалось получить список типов рабочих пространств.");
                                logger.error("Не удалось получить список типов рабочих пространств.", e);
                            }

                            try {
                                do {
                                    try {
                                        System.out.print("Укажите номер выбранного типа: ");
                                        int type = ConsoleReader.chooseMenuItem(workspaceTypes.size());

                                        if (type == 0) {
                                            break;
                                        }

                                        type--;
                                        workspaces = service.getWorkspacesByType(workspaceTypes.get(type));

                                        if (!workspaces.isEmpty()) {
                                            System.out.println("№ | Тип | Название | Вместимость | Цена за час | Статус");
                                            int i = 1;
                                            for (WorkspaceDto workspace : workspaces) {
                                                System.out.println(i + " | " + workspace.toMenuRow());
                                                i++;
                                            }
                                        }
                                        else {
                                            System.out.println("Список рабочих пространств пуст.");
                                            break;
                                        }

                                        return workspaces;
                                    } catch (ConsoleUserInputException e) {
                                        System.out.println(e.getMessage());
                                        logger.error("Ошибка консольного ввода.", e);
                                        break;
                                    }
                                } while (true);
                            } catch (SQLException e) {
                                System.out.println("Не удалось получить список рабочих пространств.");
                                logger.error("Не удалось получить список рабочих пространств.", e);
                            }
                            break;
                        case 2: // название
                            ConsoleReader.cls();
                            do {
                                try {
                                    System.out.print("Название рабочего пространства: ");
                                    String workspaceName = ConsoleReader.readString();

                                    return service.getWorkspacesByName(workspaceName);
                                } catch (SQLException e) {
                                    System.out.println("Не удалось получить список рабочих пространств.");
                                    logger.error("Не удалось получить список рабочих пространств.", e);
                                } catch (ConsoleUserInputException e) {
                                    System.out.println(e.getMessage());
                                    logger.error("Ошибка консольного ввода.", e);
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

                                    return service.getWorkspacesByCapacity(capacity);
                                } catch (SQLException e) {
                                    System.out.println("Не удалось получить список рабочих пространств.");
                                    logger.error("Не удалось получить список рабочих пространств.", e);
                                } catch (ConsoleUserInputException e) {
                                    System.out.println(e.getMessage());
                                    logger.error("Ошибка консольного ввода.", e);
                                    break;
                                }
                            } while (true);
                            break;
                        case 4: // стоимость
                            ConsoleReader.cls();
                            do {
                                try {
                                    System.out.print("Укажите минимальную часовую стоимость: ");
                                    BigDecimal minHourlyRate = new BigDecimal(ConsoleReader.readDouble());
                                    System.out.print("Укажите максимальную часовую стоимость: ");
                                    BigDecimal maxHourlyRate = new BigDecimal(ConsoleReader.readDouble());

                                    return service.getWorkspacesByHourlyRate(minHourlyRate, maxHourlyRate);
                                } catch (SQLException e) {
                                    System.out.println("Не удалось получить список рабочих пространств.");
                                    logger.error("Не удалось получить список рабочих пространств.", e);
                                } catch (ConsoleUserInputException e) {
                                    System.out.println(e.getMessage());
                                    logger.error("Ошибка консольного ввода.", e);
                                    break;
                                }
                            } while (true);
                            break;
                        case 5: // статус
                            ConsoleReader.cls();
                            do {
                                try {
                                    System.out.print("Выберите статус");
                                    System.out.println("\n1 - Активно");
                                    System.out.println("2 - Заблокировано");;
                                    System.out.print("> ");
                                    int selectedStatus = ConsoleReader.chooseMenuItem(1, 2);
                                    boolean isActive = true;
                                    switch(selectedStatus) {
                                        case 2: {
                                            isActive = false;
                                            break;
                                        }
                                    }

                                    return service.getWorkspacesByStatus(isActive);
                                } catch (SQLException e) {
                                    System.out.println("Не удалось получить список рабочих пространств.");
                                    logger.error("Не удалось получить список рабочих пространств.", e);
                                } catch (ConsoleUserInputException e) {
                                    System.out.println(e.getMessage());
                                    logger.error("Ошибка консольного ввода.", e);
                                    break;
                                }
                            } while (true);
                            break;
                        case 6: // назад
                            return new ArrayList<WorkspaceDto>();
                    }

                } catch (ConsoleUserInputException e) {
                    System.out.println(e.getMessage());
                    logger.error("Ошибка консольного ввода.", e);
                }
            } while (true);
        }

        /**
         * Поиск рабочего пространства по параметрам.
         */
        private WorkspaceDto findWorkspace() {
            ArrayList<WorkspaceDto> workspaces = getFilteredWorkspaceList();

            if (workspaces.isEmpty()) {
                System.out.println("Не нашлось рабочих пространств, удовлетворяющих фильтрам.");
                logger.debug("Не нашлось рабочих пространств, удовлетворяющих фильтрам.");
                return null;
            }
            else {
                try {
                    System.out.println("Выберите рабочее пространство.");
                    int i = 1;
                    for (WorkspaceDto workspace : workspaces) {
                        System.out.println(i + " | " + workspace.toMenuRow());
                    }
                    System.out.print("> ");

                    int selectedWorkspaceNumber = ConsoleReader.chooseMenuItem(workspaces.size());

                    return workspaces.get(selectedWorkspaceNumber-1);
                } catch (ConsoleUserInputException e) {
                    System.out.println(e.getMessage());
                    logger.error("Ошибка консольного ввода.", e);
                }
            }
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
