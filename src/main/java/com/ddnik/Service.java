package com.ddnik;

import com.ddnik.entity.Users;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;

public class Service {

    private Repository repo;
    public Service() {
        repo = new Repository();
    }

    public Users findUserByEmail(String email, String password) throws SQLException {
        // Заглушка пока не подключена БД
        if (email.equals("suprastin") && password.equals("123")) {
            return new Users(1L, "dd.nikolaenko@gmail.com", "Николаенко Дмитрий Денисович", new BigInteger("1"), false, new Date(2026, 7, 18));
        }
        if (email == "just_user" && password == "123") {
            return new Users(2L, "dd.nikolaenko@gmail.com", "Тестовый Пользователь", new BigInteger("2"), false, new Date(2026, 7, 18));
        }
        else throw new SQLException("Некорректные данные пользователя");
    }

}
