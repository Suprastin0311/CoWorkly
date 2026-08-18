package com.ddnik.controller;

import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Универсальное консольное меню для перехода к дочерним меню.
 */
public class ConsoleMenu {

    public final String title;
    private final List<MenuItem> items = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private boolean isRunning = true;

    private final Logger logger = LoggerFactory.getLogger(ConsoleMenu.class);

    public ConsoleMenu(String title) {
        this.title = title;
    }

    /**
     * Добавляет пункт в консольное меню.
     * @param title отображаемый текст пункта в меню
     * @param action логика
     */
    public void addItem(String title, MenuAction action) {
        items.add(new MenuItem(title, action));
    }

    /**
     * Запуск меню.
     */
    public void start() {
        isRunning = true;
        while (isRunning) {
            try {
                display();
                int choice = ConsoleReader.chooseMenuItem(0, items.size());

                if (choice == 0) {
                    isRunning = false; // Назад
                }
                else {
                    executeAction(items.get(choice-1)); // Выполнить действие выбранного пункта
                }
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getLocalizedMessage());
                logger.error("Ошибка консольного ввода.", e);
            }
        }
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
    private void executeAction(MenuItem item) {
        try {
            item.getAction().execute();
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка: " + e.getLocalizedMessage());
            System.out.println("Нажмите любую клавишу чтобы продолжить...");
            scanner.nextLine();
        }
    }
}
