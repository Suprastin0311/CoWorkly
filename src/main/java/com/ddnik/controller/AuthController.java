package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.Menu;
import com.ddnik.PasswordHasher;
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
        Menu.cls();

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
                            logger.info("Пользователь вошёл под ролью Admin.");
                            return new AuthorizedUser(user.get().email(), user.get().fullName(), UserRole.Admin, user.get().isBlocked());
                        }
                        else if (user.get().role().equals("User")) {
                            logger.info("Пользователь вошёл под ролью User.");
                            return new AuthorizedUser(user.get().email(), user.get().fullName(), UserRole.User,user.get().isBlocked());
                        }
                    }
                }

            } catch (SQLException e) {
                throw e;
            }
        } while (true);
    }
}