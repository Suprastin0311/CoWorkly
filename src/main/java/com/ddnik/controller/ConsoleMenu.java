package com.ddnik.controller;

import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;

/**
 * Универсальное консольное меню.
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
     * @param title отображаемый текст пункта в меню.
     * @param action логика пункта меню.
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
                System.out.println("0 - Назад");
                int choice = ConsoleReader.chooseMenuItem(0, items.size());

                if (choice == 0) isRunning = false; // Назад
                else executeAction(items.get(choice-1)); // Выполнить действие выбранного пункта
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getLocalizedMessage());
                logger.error("Ошибка консольного ввода.", e);
            }
        }
    }

    /**
     * Остановка меню.
     */
    public void close() {
        isRunning = false;
    }

    /**
     * Отображение меню.
     */
    private void display() {
        System.out.println(title + "\n");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + " - " + items.get(i).getTitle());
        }
    }

    /**
     * Выполнение пункта меню.
     * @param item логика пункта меню.
     */
    private void executeAction(MenuItem item) {
        try {
            item.getAction().execute();
        } catch (ConsoleUserInputException e) {
            System.out.println(e.getLocalizedMessage());
            logger.error("Ошибка консольного ввода.", e);
            ConsoleReader.waitInput();
        } catch (SQLException e) {
            System.out.println("Ошибка с базой данных.");
            logger.error(e.getLocalizedMessage(), e);
            ConsoleReader.waitInput();
        } catch (SecurityException e) {
            System.out.println(e.getLocalizedMessage());
            logger.error("Ошибка доступа.", e);
            ConsoleReader.waitInput();
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка.");
            logger.error(e.getLocalizedMessage(), e);
            ConsoleReader.waitInput();
        }
    }
}
