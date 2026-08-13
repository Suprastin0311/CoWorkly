package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.PasswordHasher;
import com.ddnik.SecurityContextHolder;
import com.ddnik.db.Service;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public static AuthorizedUser auth() throws SQLException {
        Service service = new Service();
        Scanner scanner = new Scanner(System.in);
        ConsoleReader.cls();

        do {
            try {
                System.out.print("Email: ");
                String email = scanner.next().trim();
                Optional<UsersDto> user = service.getUserByEmail(email);
                if (user.isEmpty()) {
                    System.out.println("Пользователь не найден.");
                }
                else {
                    System.out.print("Password: ");
                    String password = scanner.next().trim();

                    if (!PasswordHasher.checkPassword(password, user.get().passwordHash())) {
                        System.out.println("Неверный пароль.");
                    }
                    else {
                        if (user.get().role().equals("Admin")) {
                            AuthorizedUser authorizedUser = new AuthorizedUser(user.get().email(), user.get().fullName(), UserRole.Admin, user.get().isBlocked());
                            SecurityContextHolder.setLoggedUser(authorizedUser);
                            logger.info("Пользователь вошёл под ролью Admin.");
                            return authorizedUser;
                        }
                        else if (user.get().role().equals("User")) {
                            AuthorizedUser authorizedUser = new AuthorizedUser(user.get().email(), user.get().fullName(), UserRole.User, user.get().isBlocked());
                            SecurityContextHolder.setLoggedUser(authorizedUser);
                            logger.info("Пользователь вошёл под ролью User.");
                            return authorizedUser;
                        }
                    }
                }

            } catch (SQLException e) {
                throw e;
            }
        } while (true);
    }
}