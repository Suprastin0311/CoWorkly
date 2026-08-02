package com.ddnik;

import com.ddnik.enums.UserRole;

/**
 * Содержит данные авторизованного в данный момент пользователя.
 */
public class AuthorizedUser {

    private String email;
    private String fullName;
    private UserRole role;
    private boolean isBlocked;

    public AuthorizedUser(String email, String fullName, UserRole role, boolean isBlocked) {
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.isBlocked = isBlocked;
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
