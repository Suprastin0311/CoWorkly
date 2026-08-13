package com.ddnik.db.dto;

import java.sql.Date;
import java.util.Objects;

public class UsersDto {
    private final Long id;
    private final String email;
    private final String passwordHash;
    private final String fullName;
    private final String role;
    private final boolean isBlocked;
    private final Date createdAt;

    public UsersDto {
        Objects.requireNonNull(email);
        Objects.requireNonNull(passwordHash);
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(role);
        Objects.requireNonNull(createdAt);
    }

    public UsersDto(String email, String passwordHash, String fullName, String role, boolean isBlocked, Date createdAt) {
        this(null, email, passwordHash, fullName, role, isBlocked, createdAt);
    }
}
