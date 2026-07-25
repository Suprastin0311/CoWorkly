package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.Menu;
import com.ddnik.enums.UserRole;
import com.ddnik.exceptions.ConsoleUserInputException;

public class RegistrationController {

    public AuthorizedUser start() {
        Menu.cls();
        System.out.println("Авторизация (введите букву [q] для выхода.)\n");

        // Ввод email
        boolean isEmailValid = false;
        String email = "";
        do {
            try {
                System.out.print("Введите email: ");
                email = Menu.readString();
                if (email.equals("q")) return null;
                isEmailValid = Menu.validateEmail(email);

                //TODO проверка на существование записи с таким же email

                if (!isEmailValid) System.out.println("Ошибка: email не соответствует шаблону example@mail.domen");
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!isEmailValid);

        // Ввод пароля
        String password;
        do {
            try {
                System.out.print("Введите пароль: ");
                password = Menu.readString();
                if (password.equals("q")) return null;  // выход по q
                System.out.print("Повторите пароль: ");
                String repeatPassword = Menu.readString();
                if (password.equals("q")) return null; // выход по q
                if (!password.equals(repeatPassword)) { System.out.println("Пароль не совпадают."); }
                else break;

            } catch (ConsoleUserInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        // ФИО
        String fullName;
        do {
            try {
                System.out.print("Введите Фамилию Имя Отчество полностью: ");
                fullName = Menu.readString();
                break;
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        // TODO создаём запись с новым пользователем

        return new AuthorizedUser(email, UserRole.User, false);
    }

}
