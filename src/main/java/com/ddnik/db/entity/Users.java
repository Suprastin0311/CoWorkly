package com.ddnik.db.entity;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

public record Users (
        Long id,
        String email,
        String password,
        String fullName,
        long role,
        boolean isBlocked,
        Date createdAt
) {

    public Users {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(role);
        Objects.requireNonNull(isBlocked);
        Objects.requireNonNull(createdAt);
    }

    public Users(String email, String password, String fullName, long role, boolean isBlocked, Date createdAt) {
        this(null, email, password, fullName, role, isBlocked, createdAt);
    }
}


