package com.ddnik;

import com.ddnik.enums.UserRole;

import java.util.Objects;

/**
 * Содержит данные авторизованного в данный момент пользователя.
 */
public record AuthorizedUser (
        long id,
        String email,
        String fullName,
        UserRole role,
        boolean isBlocked
) {
    public AuthorizedUser {
        Objects.requireNonNull(email);
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(role);
    }
}
