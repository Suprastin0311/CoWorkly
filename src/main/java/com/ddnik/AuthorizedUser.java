package com.ddnik;

import com.ddnik.enums.UserRole;

/**
 * Содержит данные авторизованного в данный момент пользователя.
 */
public class AuthorizedUser {

    private String username;
    private UserRole role;
    private boolean isBlocked;

    public AuthorizedUser(String username, UserRole role, boolean isBlocked) {
        this.username = username;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
