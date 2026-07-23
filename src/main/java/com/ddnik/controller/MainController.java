package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.Menu;
import com.ddnik.entity.User;
import com.ddnik.enums.UserRole;
import com.ddnik.exceptions.ConsoleUserInputException;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainController {

    public static void start() {
        while (true) {
            Menu.cls();
            Menu.printMenu("hello");
            Scanner sc = new Scanner(System.in);

            int choice = sc.nextInt();
            AuthorizedUser user = new AuthorizedUser("guest", UserRole.NoAuth, false);

            if (choice == 1) {
                do {
                    try {
                        user = AuthController.auth();
                        break;
                    } catch (Exception e) {
                        System.out.println("Ошибка авторизации: " + e.getMessage());
                    }
                } while (true);

                switch (user.getRole()) {
                    case Admin -> {
                        Menu.cls();
                        AdminController ac = new AdminController(user);
                        ac.start();
                    }

                    case User -> {
                        Menu.cls();
                        UserController uc = new UserController(user);
                        uc.start();
                    }

                    case NoAuth -> {
                        System.out.println("Не удалось авторизоваться - проверьте корректность учётных данных.");
                    }
                }
            }
            else {
                System.out.println("Пользователь завершил работу с приложением.");
                return;
            }
        }
    }
}