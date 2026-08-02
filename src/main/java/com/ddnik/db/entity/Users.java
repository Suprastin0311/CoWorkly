package com.ddnik.db.entity;

import java.math.BigInteger;
import java.sql.Date;

public class Users {
    private final Long id;
    private final String email;
    private final String fullName;
    private final BigInteger role;
    private final boolean isBlocked;
    private final Date createdAt;

    public Users(Long id, String email, String fullName, BigInteger role, boolean isBlocked, Date createdAt) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.isBlocked = isBlocked;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public BigInteger getRole() {
        return role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}


