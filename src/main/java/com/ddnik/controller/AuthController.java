package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.Menu;
import com.ddnik.Service;
import com.ddnik.entity.Users;
import com.ddnik.enums.UserRole;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthController {

    public static AuthorizedUser auth() throws Exception {
        Service service = new Service();
        Scanner scanner = new Scanner(System.in);
        Users user;
        Menu.cls();

        do {
            try {
                System.out.print("Email: ");
                String email = scanner.next().trim();
                System.out.print("Password: ");
                String password = scanner.next().trim();
                user = service.findUserByEmail(email, password);
                if (user != null) {
                    if (user.getRole().equals("admin")) {
                        //TODO получить пользователя
                        return new AuthorizedUser("dd.nikolaenko@gmail.com", UserRole.Admin, false);
                    }
                    else if (user.getRole().equals("user")) {
                        //TODO получить пользователя
                        return new AuthorizedUser("dd.nikolaenko@yandex.ru", UserRole.User, false);
                    }
                }
                else {
                    throw new Exception("Пользователь не найден.");
                }
                scanner.close();

            } catch (SQLException e) {
                throw new  Exception(e.getLocalizedMessage(), e);
            }
        } while (true);
    }
}