package com.ddnik.entity;

import java.sql.Date;

public class User {
    private long id;
    private String email;
    private String fullName;
    private String role;
    private boolean isBlocked;
    private Date createdAt;

    public User(long id, String email, String fullName, String role, boolean isBlocked, Date createdAt) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.isBlocked = isBlocked;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}


