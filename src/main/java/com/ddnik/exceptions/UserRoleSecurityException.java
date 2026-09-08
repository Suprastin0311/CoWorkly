package com.ddnik.exceptions;

import com.ddnik.enums.UserRole;

/**
 * Исключение, выбрасываемое сервисом в случае, если у авторизованного пользователя недостаточно прав для выполнения операции.
 */
public class UserRoleSecurityException extends SecurityException {
    private final UserRole currentUserRole;
    private final UserRole[] requiredUserRoles;

    /**
     * Создаёт объект исключения.
     * @param message сообщение.
     * @param currentUserRole роль авторизованного пользователя.
     * @param requiredRoles массив ролей, обладающих достаточными правами для выполнения операции.
     */
    public UserRoleSecurityException(String message, UserRole currentUserRole, UserRole... requiredRoles) {
        super(message);
        this.currentUserRole = currentUserRole;
        this.requiredUserRoles = requiredRoles;
    }

    /**
     * Создаёт объект исключения.
     * @param message сообщение.
     * @param cause причина.
     * @param currentUserRole роль авторизованного пользователя.
     * @param requiredRoles массив ролей, обладающих достаточными правами для выполнения операции.
     */
    public UserRoleSecurityException(String message, Throwable cause, UserRole currentUserRole, UserRole... requiredRoles) {
        super(message, cause);
        this.currentUserRole = currentUserRole;
        this.requiredUserRoles = requiredRoles;
    }

    /**
     * Создаёт объект исключения.
     * @param cause причина.
     * @param currentUserRole роль авторизованного пользователя.
     * @param requiredRoles массив ролей, обладающих достаточными правами для выполнения операции.
     */
    public UserRoleSecurityException(Throwable cause, UserRole currentUserRole, UserRole... requiredRoles) {
        super(cause);
        this.currentUserRole = currentUserRole;
        this.requiredUserRoles = requiredRoles;
    }

    /**
     * Создаёт объект исключения.
     * @param currentUserRole роль авторизованного пользователя.
     * @param requiredRoles массив ролей, обладающих достаточными правами для выполнения операции.
     */
    public UserRoleSecurityException(UserRole currentUserRole, UserRole... requiredRoles) {
        this.currentUserRole = currentUserRole;
        this.requiredUserRoles = requiredRoles;
    }

    /**
     * Получить роль пользователя, пытавшегося выполнить операцию.
     * @return роль авторизованного пользователя.
     */
    public UserRole getCurrentUserRole() {
        return currentUserRole;
    }

    /**
     * Получить массив ролей пользователя, обладающих достаточными правами для выполнения операции.
     * @return массив допустимых ролей.
     */
    public UserRole[] getRequiredUserRoles() {
        return requiredUserRoles;
    }
}
