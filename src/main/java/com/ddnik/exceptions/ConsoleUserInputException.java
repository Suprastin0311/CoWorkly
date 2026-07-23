package com.ddnik.exceptions;

import com.ddnik.enums.UserRole;

/**
 * Исключение, выбрасываемое при работе с консольным меню: ввод несуществующего пункта меню, ошибочный ввод и пр.
 */
public class ConsoleUserInputException extends Exception {

    private final String    mode;
    private final UserRole role;

    public ConsoleUserInputException(String mode, UserRole role) {
        this.mode = mode;
        this.role = role;
    }

    public ConsoleUserInputException(String message, String mode, UserRole role) {
        super(message);
        this.mode = mode;
        this.role = role;
    }

    public ConsoleUserInputException(String message, Throwable cause, String mode, UserRole role) {
        super(message, cause);
        this.mode = mode;
        this.role = role;
    }

    public ConsoleUserInputException(Throwable cause, String mode, UserRole role) {
        super(cause);
        this.mode = mode;
        this.role = role;
    }

    public ConsoleUserInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String mode, UserRole role) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.mode = mode;
        this.role = role;
    }

    public ConsoleUserInputException(String message, Throwable cause) {
        super(message, cause);
        this.mode = "unknown";
        this.role = UserRole.NoAuth;
    }

    public ConsoleUserInputException(String message) {
        super(message);
        this.mode = "unknown";
        this.role = UserRole.NoAuth;
    }
}
