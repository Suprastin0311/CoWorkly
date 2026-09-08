package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.PasswordHasher;
import com.ddnik.SecurityContextHolder;
import com.ddnik.db.Service;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.enums.UserRole;
import com.ddnik.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public static AuthorizedUser auth() throws DatabaseException {
        Service service = new Service(UserRole.NoAuth);
        ConsoleReader.cls();

        while (true) {
            Optional<String> email = ConsoleReader.readEmail();
            if (email.isEmpty()) return new AuthorizedUser(-1L, "", "",  UserRole.NoAuth, true);

            Optional<UsersDto> user = service.getUserByEmail(email.get());
            if (user.isEmpty()) Out.printlnRed("Пользователь не найден.");
            else {
                Optional<String> password = ConsoleReader.readString("Password");
                if (password.isEmpty()) return new AuthorizedUser(-1L, "", "", UserRole.NoAuth, true);

                if (!PasswordHasher.checkPassword(password.get(), user.get().passwordHash()))
                    Out.printlnRed("Неверный пароль.");
                else {
                    if (user.get().role().name().equals("Admin")) {
                        AuthorizedUser authorizedUser = new AuthorizedUser(user.get().id(), user.get().email(), user.get().fullName(), UserRole.Admin, user.get().isBlocked());
                        SecurityContextHolder.setLoggedUser(authorizedUser);
                        logger.info("Пользователь вошёл под ролью Admin.");
                        return authorizedUser;
                    }
                    else if (user.get().role().name().equals("User")) {
                        AuthorizedUser authorizedUser = new AuthorizedUser(user.get().id(), user.get().email(), user.get().fullName(), UserRole.User, user.get().isBlocked());
                        SecurityContextHolder.setLoggedUser(authorizedUser);
                        logger.info("Пользователь вошёл под ролью User.");
                        return authorizedUser;
                    }
                }
            }
        }
    }
}