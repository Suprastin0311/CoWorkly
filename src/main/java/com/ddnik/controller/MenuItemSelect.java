package com.ddnik.controller;

import java.lang.invoke.TypeDescriptor;

public class MenuItemSelect<T> {
    private final String title;
    private final MenuActionSelect<T> action;

    public MenuItemSelect(String title, MenuActionSelect<T> action) {
        this.title = title;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public MenuActionSelect<T> getAction() {
        return action;
    }
}
