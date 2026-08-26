package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    public void start() {
        ConsoleMenu menu = new ConsoleMenu("Добро пожаловать в CoWorkly!");
        menu.addItem("Войти", this::login);
        menu.addItem("Зарегистрироваться", this::registration);

        logger.info("Пользователь перешёл в главное меню.");
        menu.start();
    }

    private void login() {
        try {
            AuthorizedUser user = AuthController.auth();

            switch (user.getRole()) {
                case UserRole.NoAuth -> {
                    Out.printlnRed("Не удалось авторизоваться.");
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

        } catch (SQLException e) {
            Out.printlnRed("Возникла ошибка c базой данных.");
            logger.error("Ошибка базы данных", e);
        }
    }

    private void registration() {
        RegistrationController regController = new RegistrationController();
        regController.start();
    }
}