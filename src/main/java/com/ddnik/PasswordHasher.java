package com.ddnik;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Шифрует пароль и проверяет пароль при авторизации.
 */
public class PasswordHasher {

    /**
     * Зашифровать пароль алгоритмом bcrypt.
     * @param password пароль.
     * @return хэш.
     */
    public static String hashPassword(String password) {
        int logRounds = 12;

        String salt = BCrypt.gensalt(logRounds);

        return BCrypt.hashpw(password, salt);
    }

    /**
     * Проверить совпадение пароля с хэшем пароля из БД.
     * @param password пароль.
     * @param hash хэш пароля.
     * @return true - пароль верный,<br>false - пароль неверный
     */
    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
