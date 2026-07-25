package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.enums.UserRole;
import com.ddnik.exceptions.ConsoleUserInputException;

public class MainController {

    public void start() {
        ConsoleMenu menu = new ConsoleMenu("Добро пожаловать в CoWorkly!");
        menu.addItem("Войти", this::login);
        menu.addItem("Зарегистрироваться", this::registration);

        menu.start();
    }

    private void login() throws ConsoleUserInputException {
        try {
            AuthController authController = new AuthController();
            AuthorizedUser user = authController.auth();

            if (user != null) {
                switch (user.getRole()) {
                    case UserRole.NoAuth -> {
                        System.out.println("Не удалось авторизоваться.");
                        return;
                    }
                    case UserRole.Admin -> {
                        AdminController ac = new AdminController(user);
                        ac.start();
                    }
                    case UserRole.User -> {
                        UserController uc = new UserController(user);
                        uc.start();
                    }
                }
            }
            else {
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void registration() {
        RegistrationController regController = new RegistrationController();
        regController.start();
    }
}