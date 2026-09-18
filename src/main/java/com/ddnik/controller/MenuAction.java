package com.ddnik.controller;

/**
 * Функциональный интерфейс, реализует логику пункта меню.
 */
@FunctionalInterface
public interface MenuAction {
    void execute() throws Exception;
}
