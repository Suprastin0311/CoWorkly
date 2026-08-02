package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.Menu;
import com.ddnik.PasswordHasher;
import com.ddnik.db.Service;
import com.ddnik.db.dto.UsersDto;
import com.ddnik.db.entity.Users;
import com.ddnik.enums.UserRole;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthController {

    public static AuthorizedUser auth() throws Exception {
        Service service = new Service();
        Scanner scanner = new Scanner(System.in);
        UsersDto user;
        Menu.cls();

        do {
            try {
                System.out.print("Email: ");
                String email = scanner.next().trim();
                user = service.getUserByEmail(email);

                System.out.print("Password: ");
                String password = scanner.next().trim();

                if (PasswordHasher.checkPassword(password, user.passwordHash())) {
                    if (user.role().equals("Admin")) {
                        return new AuthorizedUser(user.email(), user.fullName(), UserRole.Admin, user.isBlocked());
                    }
                    else if (user.role().equals("User")) {
                        return new AuthorizedUser(user.email(), user.fullName(), UserRole.User,user.isBlocked());
                    }
                }
                else {
                    System.out.println("Неверный пароль.");
                }
            } catch (SQLException e) {
                throw new Exception(e.getLocalizedMessage(), e);
            } catch (Exception e) {
                throw e;
            }
        } while (true);
    }
}