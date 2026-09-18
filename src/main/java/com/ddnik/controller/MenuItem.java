package com.ddnik.controller;

/**
 * Пункт консольного меню. Содержит название и ссылку на реализацию логики.
 */
public class MenuItem {
    private final String title;
    private final MenuAction action;

    public MenuItem(String title, MenuAction action) {
        this.title = title;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public MenuAction getAction() {
        return action;
    }
}
