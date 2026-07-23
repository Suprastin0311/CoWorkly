package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.Menu;
import com.ddnik.entity.Booking;
import com.ddnik.entity.BookingStatus;
import com.ddnik.entity.User;
import com.ddnik.entity.Workspace;
import com.ddnik.exceptions.ConsoleUserInputException;

import java.sql.Date;
import java.util.Scanner;

public class AdminController {

    private final AuthorizedUser user;

    public AdminController(AuthorizedUser user) {
        this.user = user;
    }

    /**
     * Запуск панели администратора.
     */
    public void start() {
        Menu.cls();
        Scanner sc = new Scanner(System.in);

        do {
            Menu.printMenu("admin");

            int choice = sc.nextInt();
            switch (choice) {
                case 1: // посмотреть пользователей
                    users();
                    Menu.cls();
                    break;
                case 2: // посмотреть рабочие пространства
                    workspaces();
                    Menu.cls();
                    break;
                case 3: // посмотреть брони
                    bookings();
                    Menu.cls();
                    break;
                case 4: // Назад
                    return;
            }
        } while (true);
    }

    /**
     * Меню работы с пользователями.
     */
    private void users() {
        Scanner sc = new Scanner(System.in); //TODO сделать нормально через Menu
        Menu.cls();

        do {
            Menu.printMenu("users");

            int choice = sc.nextInt();
            switch (choice) {
                case 1: // найти пользователя
                    viewUsers();
                    Menu.cls();
                    break;
                case 2: // посмотреть пользователей
                    Menu.cls();
                    //TODO вывод всех пользователей
                    System.out.println("Тут будут все пользователи.");
                    break;
                case 3: // редактировать пользователя
                    manageUser();
                    Menu.cls();
                    break;
                case 4: // Назад
                    return;
            }
        } while (true);
    }

    /**
     * Вывод информации о пользователях.
     */
    private void viewUsers() {
        System.out.println("Тут будет вывод всех пользователей.");
    }

    /**
     * Управление аккаунтом пользователя.
     */
    private void manageUser() {
        Menu.cls();
        Scanner sc = new Scanner(System.in);
        User selectedUser;

        do {
            Menu.printMenu("manageUser");

            int choice = sc.nextInt();
            switch (choice) {
                case 1: // редактирование
                    do {
                        try {
                            selectedUser = findUser();
                            System.out.println("Тут будут выбранные пользователи.");

                            // TODO редактирование пользователя
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    } while (true);
                    break;
                case 2: // заблокировать / разблокировать
                    do {
                        try {
                            selectedUser = findUser();
                            System.out.print("Укажите номер пользователя для блокировки / разблокировка.");
                            // TODO блокировка пользователя

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    } while (true);
                    break;
                case 3: // Удаление
                    do {
                        try {
                            selectedUser = findUser();
                            System.out.print("Укажите номер пользователя для удаления.");
                            //TODO удаление пользователя

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    } while(true);
                    break;
                case 4:
                    return;
            }
        } while (true);
    }

    /**
     * Поиск пользователя по данным учётной записи.
     * @return модель данных пользователя
     */
    private User findUser() throws Exception {
        Menu.cls();
        Scanner sc = new Scanner(System.in);

        do {
            Menu.printMenu("findUser");

            int choice = sc.nextInt();
            switch (choice) { // user = switch() -> yield
                case 1: // Email
                    Menu.cls();
                    boolean isEmailValid;
                    String email;
                    do {
                        System.out.print("Введите email (пример: exmaple@some.domen): ");
                        email = sc.next().trim();
                        isEmailValid = Menu.validateEmail(email);
                        if (isEmailValid) {
                            break;
                            // return User
                        }
                        System.out.println("\nОшибка. Убедитесь, что вводимый email удовлетворяет маске.");
                    } while (!isEmailValid);
                    break;
                case 2: // ФИО
                    Menu.cls();
                    System.out.print("Введите ФИО: ");
                    String name = sc.next().trim();

                    // return User;
                    break;
                case 3: // Роль
                    Menu.cls();
                    Menu.printMenu("findUserByRole");

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
                            Menu.cls();
                            break;
                    }

                    break;
                case 4: // Назад
                    throw new Exception("Отмена поиска.");
            }
        } while (true);
    }

    /**
     * Меню работы с рабочими пространствами.
     */
    private void workspaces() {
        Menu.cls();

        do {
            Menu.printMenu("workspaces");

            try {
                int choice = Menu.chooseMenuItem(4);

                switch (choice) {
                    case 1: // вывести все
                        seeWorkspaces();
                        break;
                    case 2: // управление
                        manageWorkspaces();
                        break;
                    case 3: // Назад
                        return;
                }
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getMessage());
                break;
            }
        } while (true);
    }

    /**
     * Просмотреть рабочие пространства.
     */
    private void seeWorkspaces() {
        Menu.cls();
        System.out.println("Тут будет выбранное рабочее пространство.");
        //TODO красивый вывод рабочего пространства
    }

    public void manageWorkspaces() {
        Menu.cls();

        do {
            Menu.printMenu("manageWorkspaces");

            try {
                int choice = Menu.chooseMenuItem(4);
                Workspace selectedWorkspace;

                switch (choice) {
                    case 1: // редактировать
                        selectedWorkspace = findWorkspace();

                        editWorkspace(selectedWorkspace);
                        break;
                    case 2: // активация / деактивация
                        selectedWorkspace = findWorkspace();

                        activateWorkspace(selectedWorkspace);
                        break;
                    case 3: // Создание
                        createWorkspace();
                        break;
                    case 4: // Удаление
                        selectedWorkspace = findWorkspace();

                        deleteWorkspace(selectedWorkspace);
                        break;
                    case 5:
                        return;
                }
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getMessage());
                break;
            }
        } while (true);
    }

    private void activateWorkspace(Workspace workspace) {
        System.out.println("Деактивация рабочего пространства");
    }

    private void editWorkspace(Workspace workspace) {
        System.out.println("Редактирование рабочего пространства");
    }

    private void createWorkspace() {
        System.out.println("Создание рабочего пространства");
    }

    private void deleteWorkspace(Workspace workspace) {
        System.out.println("Удаление рабочего пространства");
    }

    /**
     * Поиск рабочего пространства по параметрам.
     */
    private Workspace findWorkspace() { // возвращать Workspace
        Menu.cls();
        Menu.printMenu("findWorkspace");

        do {
            try {
                int choice = Menu.chooseMenuItem(6);

                switch (choice) {
                    case 1: // тип
                        Menu.cls();
                        Menu.printMenu("findWorkspaceByType");

                        do {
                            try {
                                int selectType = Menu.chooseMenuItem(3);

                                if (selectType == 1) {
                                    // TODO добавить тип в поиск
                                }
                                else if (selectType == 2) {
                                    // TODO добавить тип в поиск
                                }
                                else if (selectType == 3) {
                                    break;
                                }

                                // TODO найти и вывести рабочие пространства
                                System.out.print("Выберите рабочее пространство: ");

                                int selectedWorkspace = Menu.chooseMenuItem(0); // TODO указать количество рабочих пространств
                                // TODO return Workspace

                            } catch (ConsoleUserInputException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            break;
                        } while (true);
                    case 2: // название
                        Menu.cls();
                        do {
                            try {
                                System.out.print("Название рабочего пространства: ");
                                String workspaceName = Menu.readString();

                                // TODO найти и вывести рабочие пространства

                                System.out.print("Выберите рабочее пространство: ");

                                int selectedWorkspace = Menu.chooseMenuItem(0); // TODO указать количество рабочих пространств
                                // TODO return Workspace
                            } catch (ConsoleUserInputException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                        } while (true);
                        break;
                    case 3: // вместимость
                        Menu.cls();
                        do {
                            try {
                                System.out.print("Укажите вместимость: ");
                                int capacity = Menu.readPositiveInt();

                                // TODO найти и вывести рабочие пространства

                                System.out.print("Выберите рабочее пространство: ");

                                int selectedWorkspace = Menu.chooseMenuItem(0); // TODO указать количество рабочих пространств
                                // TODO return Workspace
                            } catch (ConsoleUserInputException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                        } while (true);
                        break;
                    case 4: // стоимость
                        Menu.cls();
                        do {
                            try {
                                System.out.print("Укажите часовую стоимость: ");
                                double hourRate = Menu.readDouble();

                                // TODO найти и вывести рабочие пространства

                                System.out.print("Выберите рабочее пространство: ");

                                int selectedWorkspace = Menu.chooseMenuItem(0); // TODO указать количество рабочих пространств
                                // TODO return Workspace
                            } catch (ConsoleUserInputException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                        } while (true);
                        break;
                    case 5: // статус
                        Menu.cls();
                        do {
                            try {
                                Menu.printMenu("findWorkspaceByStatus");
                                int selectedStatus = Menu.chooseMenuItem(3);

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

                                int selectedWorkspace = Menu.chooseMenuItem(0); // TODO указать количество рабочих пространств
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

    private void bookings() {
        Menu.cls();
        do {
            Menu.printMenu("bookings");

            try {
                int choice = Menu.chooseMenuItem(5);

                switch (choice) {
                    case 1: // по пользователю
                        do {
                            try {
                                User selectedUser = findUser();

                                // TODO вывод всех броней пользователя
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                        } while (true);
                        break;
                    case 2: // по пространству
                        do {
                            try {
                                Workspace selectedWorkspace = findWorkspace();

                                // TODO вывод всех броней рабочего пространства
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                        } while (true);
                        break;
                    case 3: // по дате
                        do {
                            try {
                                Date selectedDate = Menu.readDate();

                                // TODO вывод всех броней по времени
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                        } while (true);
                        break;
                    case 4: // по статусу
                        do {
                            try {
                                BookingStatus selectedBookingStatus = selectBookingStatus();
                            } catch (ConsoleUserInputException e) {
                                break;
                            }
                        } while (true);
                        break;
                    case 5: // назад
                        return;
                }

            } catch (ConsoleUserInputException e) {
                System.out.println(e.getMessage());
                break;
            }
        } while (true);
    }

    private BookingStatus selectBookingStatus() throws ConsoleUserInputException {
        Menu.cls();
        do {

        } while (true);
    }

}
