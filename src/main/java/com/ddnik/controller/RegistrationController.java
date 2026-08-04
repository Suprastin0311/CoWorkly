package com.ddnik.controller;

import com.ddnik.Menu;
import com.ddnik.db.Service;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.entity.Users;
import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.time.Instant;
import java.util.Optional;

public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private Service service;

    RegistrationController() {
        service = new Service();
    }

    public boolean start() {
        Menu.cls();
        System.out.println("Регистрация (введите букву [q] для выхода.)\n");

        // Ввод email
        boolean isEmailValid = false,
                nextStep = false;
        String email = "";
        do {
            try {
                System.out.print("Введите email: ");
                email = Menu.readString();
                if (email.equals("q")) return false;
                isEmailValid = Menu.validateEmail(email);

                if (isEmailValid) {
                    try {
                        Optional<UsersDto> user = service.getUserByEmail(email);
                        if (user.isPresent()) {
                            System.out.println("Пользователь с таким email уже существует.");
                            return false;
                        }
                        else {
                            nextStep = true;
                        }
                    } catch (SQLException e) {
                        System.out.println("Возникла ошибка с базой данных.");
                    }
                }
                else {
                    System.out.println("Ошибка: email не соответствует шаблону example@mail.domen");
                }
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!nextStep);

        nextStep = false;
        // Ввод пароля
        String password = "";
        do {
            try {
                System.out.print("Введите пароль: ");
                password = Menu.readString();
                if (password.equals("q")) return false;  // выход по q
                System.out.print("Повторите пароль: ");
                String repeatPassword = Menu.readString();
                if (repeatPassword.equals("q")) return false; // выход по q
                if (!password.equals(repeatPassword)) {
                    System.out.println("Пароль не совпадают.");
                } else {
                    nextStep = true;
                }
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!nextStep);

        nextStep = false;
        // ФИО
        String fullName = "";
        do {
            try {
                System.out.print("Введите Фамилию Имя Отчество полностью: ");
                fullName = Menu.readString();
                nextStep = true;
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!nextStep);

        try {
            Users newUser = new Users(email, password, fullName, 2, false, new Date(Instant.now().toEpochMilli()));
            service.createUser(newUser);
            logger.info("Создан новый пользователь: email - {}, fullName - {}", email, fullName);
            return true;
        } catch (SQLException e) {
            logger.info("Ошибка создания пользователя", e);
            return false;
        }
    }
}
