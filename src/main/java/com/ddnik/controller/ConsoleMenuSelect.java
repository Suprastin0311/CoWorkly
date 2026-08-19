package com.ddnik.controller;

import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Универсальное консольное меню для перехода к дочерним меню с возвратом объекта.
 */
public class ConsoleMenuSelect<T> {
    public final String title;
    private final List<MenuItemSelect<T>> items = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private boolean isRunning = true;

    private final Logger logger = LoggerFactory.getLogger(ConsoleMenuSelect.class);

    public ConsoleMenuSelect(String title) {
        this.title = title;
    }

    /**
     * Добавляет пункт в консольное меню.
     * @param title отображаемый текст пункта в меню
     * @param action логика
     */
    public void addItem(String title, MenuActionSelect<T> action) {
        items.add(new MenuItemSelect<T>(title, action));
    }

    /**
     * Запуск меню.
     */
    public Optional<T> start() {
        isRunning = true;
        Optional<T> result = Optional.empty();
        while (isRunning) {
            try {
                display();
                int choice = ConsoleReader.chooseMenuItem(0, items.size());

                if (choice == 0) {
                    isRunning = false; // Назад
                }
                else {
                    result = executeAction(items.get(choice-1)); // Выполнить действие выбранного пункта
                }
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getLocalizedMessage());
                logger.error("Ошибка консольного ввода.", e);
            }
        }
        return result;
    }

    /**
     * Отображение меню.
     */
    private void display() {
        System.out.println(title + "\n");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + " - " + items.get(i).getTitle());
        }
        System.out.println("0 - Назад");
        System.out.print("> ");
    }

    /**
     * Выполнение пункта меню
     * @param item пункт меню
     */
    private Optional<T> executeAction(MenuItemSelect<T> item) {
        Optional<T> result = Optional.empty();
        try {
            result = item.getAction().execute();
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка: " + e.getLocalizedMessage());
            System.out.println("Нажмите любую клавишу чтобы продолжить...");
            scanner.nextLine();
        }
        return result;
    }
}
