package com.ddnik.controller;

import com.ddnik.db.Service;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.entity.Users;
import com.ddnik.enums.UserRole;
import com.ddnik.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final Service service;

    RegistrationController() {
        service = new Service(UserRole.NoAuth);
    }

    public boolean start() {
        ConsoleReader.cls();
        Out.println("Регистрация\n");

        // Ввод email
        Optional<String> email = ConsoleReader.readEmail();
        if (email.isEmpty()) return false;
        try {
            Optional<UsersDto> user = service.getUserByEmail(email.get());
            if (user.isPresent()) {
                Out.printlnRed("Пользователь с таким email уже существует.");
                return false;
            }
        } catch (DatabaseException e) {
            logger.error(e.getLocalizedMessage(), e);
            Out.printlnRed("Ошибка: " + e.getLocalizedMessage());
        }

        // Ввод пароля
        Optional<String> password = ConsoleReader.readString("Введите пароль");
        if (password.isEmpty()) return false;

        boolean next = false;
        while (!next) {
            Optional<String> repeatPassword = ConsoleReader.readString("Повторите пароль");
            if (repeatPassword.isEmpty()) return false;

            if (!password.equals(repeatPassword)) Out.printlnRed("Пароль не совпадают.");
            else next = true;
        }

        // ФИО
        Optional<String>fullName = ConsoleReader.readString("Введите Фамилию Имя Отчество полностью");
        if (fullName.isEmpty()) return false;

        try {
            Users newUser = new Users(email.get(), password.get(), fullName.get(), 2, false, new Date(Instant.now().toEpochMilli()));
            service.createUser(newUser);
            logger.info("Создан новый пользователь: email - {}, fullName - {}", email, fullName);
            return true;
        } catch (DatabaseException e) {
            logger.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
}
