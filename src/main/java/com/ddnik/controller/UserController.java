package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Управляет консольным меню пользователя.
 */
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private AuthorizedUser user;

    public UserController(AuthorizedUser user) {
        this.user = user;
    }

    public void start() {
        ConsoleMenu menu = new ConsoleMenu("Вы вошли как Пользователь");
        menu.addItem("Посмотреть свободные рабочие пространства", this::viewFreeWorkspaces);
        menu.addItem("Забронировать рабочее пространство", this::bookWorkspace);
        menu.addItem("Просмотреть свои брони", this::viewBookings);
        menu.addItem("Выгрузить список броней в файл", this::report);

        logger.info("пользователь перешёл в меню.");
        menu.start();
    }

    /**
     * Просмотреть все свободные рабочие пространства.
     */
    private void viewFreeWorkspaces() {

    }

    /**
     * Забронировать.
     */
    private void bookWorkspace() {

    }

    /**
     * Просмотреть свои брони.
     */
    private void viewBookings() {

    }

    /**
     * Выгрузить список своих броней в файл формата CSV.
     */
    private void report() {

    }

    /**
     * Модель данных временного промежутка.
     */
    class DatesFilter {
        private final Date left;
        private final Date right;

        public DatesFilter(Date left, Date right) {
            this.left = left;
            this.right = right;
        }

        public Date getLeft() {
            return left;
        }

        public Date getRight() {
            return right;
        }
    }
}
