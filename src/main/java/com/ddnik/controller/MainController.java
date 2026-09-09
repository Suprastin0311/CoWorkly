package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.SecurityContextHolder;
import com.ddnik.enums.UserRole;
import com.ddnik.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Главный контроллер приложения.
 */
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * Запуск главного меню.
     */
    public void start() {
        ConsoleReader.cls();
        ConsoleMenu menu = new ConsoleMenu("Добро пожаловать в CoWorkly!");
        menu.addItem("Войти", this::login);
        menu.addItem("Зарегистрироваться", this::registration);

        logger.info("Пользователь перешёл в главное меню.");
        menu.start();
    }

    /**
     * Авторизация в системе.
     */
    private void login() {
        try {
            AuthorizedUser user = AuthController.auth();

            if (user.isBlocked()) {
                Out.printlnYellow("Не удалось войти: Ваш профиль заблокирован.");
                ConsoleReader.waitInput();
                logger.info("Пользователь с email {} не  смог войти в систему: пользователь заблокирован.", user.email());
                return;
            }

            switch (user.role()) {
                case UserRole.NoAuth -> {
                    Out.printlnRed("Не удалось авторизоваться.");
                }
                case UserRole.Admin -> {
                    AdminController ac = new AdminController();
                    ac.start();
                }
                case UserRole.User -> {
                    UserController uc = new UserController();
                    uc.start();
                }
                default -> SecurityContextHolder.clear();
            }

        } catch (DatabaseException e) {
            Out.printlnRed("Не удалось войти - возникла ошибка c базой данных.");
            logger.error("Ошибка базы данных", e);
        }
    }

    /**
     * Регистрация в системе.
     */
    private void registration() {
        RegistrationController regController = new RegistrationController();
        if (regController.start()) Out.printlnGreen("Регистрация прошла успешно!");
        else Out.printlnRed("Регистрация не удалась :(");
        ConsoleReader.waitInput();
    }
}