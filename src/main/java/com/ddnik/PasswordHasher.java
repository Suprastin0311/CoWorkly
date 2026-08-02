package com.ddnik;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    /**
     * Шифрует пароль алгоритмом bcrypt и возвращает строку с хэшом.
     * @param password пароль
     * @return хэш
     */
    public static String hashPassword(String password) {
        int logRounds = 12;

        String salt = BCrypt.gensalt(logRounds);

        return BCrypt.hashpw(password, salt);
    }

    /**
     * Проверяет совпадение пароля с хэшированным из БД
     * @param password пароль
     * @param hash хэш пароля
     * @return true - пароль верный,<br>false - пароль неверный
     */
    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
