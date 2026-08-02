package com.ddnik.db.entity;

import java.math.BigInteger;
import java.sql.Date;

public class Users {
    private final Long id;
    private final String email;
    private final String password;
    private final String fullName;
    private final long role;
    private final boolean isBlocked;
    private final Date createdAt;

    public Users(Long id, String email, String password, String fullName, long role, boolean isBlocked, Date createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.isBlocked = isBlocked;
        this.createdAt = createdAt;
    }

    public Users(String email, String password, String fullName, long role, boolean isBlocked, Date createdAt) {
        this.id = null;
        this.createdAt = createdAt;
        this.isBlocked = isBlocked;
        this.role = role;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public long getRole() {
        return role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}


