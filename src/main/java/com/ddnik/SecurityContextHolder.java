package com.ddnik;

/**
 * Хранит в локальном потоке объект авторизованного пользователя.
 */
public class SecurityContextHolder {

    private static final ThreadLocal<AuthorizedUser> currentUser = new ThreadLocal<>();

    public static void setLoggedUser(AuthorizedUser user) {
        currentUser.set(user);
    }

    public static AuthorizedUser getLoggedUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
