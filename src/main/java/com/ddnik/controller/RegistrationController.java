package com.ddnik.controller;

import com.ddnik.db.Service;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.entity.Users;
import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private Service service;

    RegistrationController() {
        service = new Service();
    }

    public boolean start() {
        ConsoleReader.cls();
        Out.println("Регистрация (введите букву [q] для выхода.)\n");

        boolean nextStep = false;

        // Ввод email
        Optional<String> email;
        email = ConsoleReader.readEmail();
        if (email.isEmpty()) return false;
        try {
            Optional<UsersDto> user = service.getUserByEmail(email.get());
            if (user.isPresent()) {
                Out.printlnRed("Пользователь с таким email уже существует.");
                return false;
            }
            else nextStep = true;
        } catch (SQLException e) {
            Out.printlnRed("Возникла ошибка с базой данных.");
        }

        // Ввод пароля
        Optional<String> password;
        do {
            password = ConsoleReader.readString("Введите пароль");
            if (password.isEmpty()) return false;

            Optional<String> repeatPassword = ConsoleReader.readString("Повторите пароль");
            if (repeatPassword.isEmpty()) return false;

            if (!password.equals(repeatPassword)) Out.printlnRed("Пароль не совпадают.");
            else nextStep = true;
        } while (nextStep);

        // ФИО
        Optional<String>fullName = ConsoleReader.readString("Введите Фамилию Имя Отчество полностью");
        if (fullName.isEmpty()) return false;

        try {
            Users newUser = new Users(email.get(), password.get(), fullName.get(), 2, false, new Date(Instant.now().toEpochMilli()));
            service.createUser(newUser);
            logger.info("Создан новый пользователь: email - {}, fullName - {}", email, fullName);
            return true;
        } catch (SQLException e) {
            logger.error("Ошибка создания пользователя", e);
            return false;
        }
    }
}
