package com.ddnik;

import com.ddnik.entity.User;

import java.sql.Date;
import java.sql.SQLException;

public class Service {

    private Repository repo;
    public Service() {
        repo = new Repository();
    }

    public User findUserByEmail(String email, String password) throws SQLException {
        // Заглушка пока не подключена БД
        if (email.equals("suprastin") && password.equals("123")) {
            return new User(1, "dd.nikolaenko@gmail.com", "Николаенко Дмитрий Денисович", "admin", false, new Date(2026, 7, 18));
        }
        if (email == "just_user" && password == "123") {
            return new User(2, "dd.nikolaenko@gmail.com", "Тестовый Пользователь", "user", false, new Date(2026, 7, 18));
        }
        else throw new SQLException("Некорректные данные пользователя");
    }

}
