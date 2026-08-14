package com.ddnik.db.dto;

import java.sql.Date;

public class UsersDto implements IDto {
    private final long id;
    private final String email;
    private final String passwordHash;
    private final String fullName;
    private final String role;
    private final boolean isBlocked;
    private final Date createdAt;

    @Override
    public String toMenuRow() {
        StringBuilder sb = new StringBuilder();
        sb.append(email).append(" | ")
                .append(passwordHash).append(" | ")
                .append(fullName).append(" | ")
                .append(role).append(" | ")
                .append(isBlocked).append(" | ")
                .append(createdAt);
        return sb.toString();
    }

    public UsersDto(long id, String email, String passwordHash, String fullName, String role, boolean isBlocked, Date createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.isBlocked = isBlocked;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
