package com.ddnik.controller;

import com.ddnik.db.entity.BookingStatuses;
import com.ddnik.db.entity.WorkspaceTypes;
import com.ddnik.exceptions.ConsoleUserInputException;

import java.util.*;

/**
 * Универсальное консольное меню.
 */
public class ConsoleMenu {

    public final String title;
    private final List<MenuItem> items = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private boolean isRunning = true;

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
                int choice = chooseMenuItem(items.size());

                if (choice == 0) {
                    isRunning = false; // Назад
                }
                else {
                    executeAction(items.get(choice-1)); // Выполнить действие выбранного пункта
                }
            } catch (ConsoleUserInputException e) {
                System.out.println(e.getLocalizedMessage());
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

    /**
     * Читает положительное целое число из консоли.
     * @return ведённое положительное целое число
     * @throws ConsoleUserInputException если произошла ошибка при вводе.
     */
    public int readPositiveInt() throws ConsoleUserInputException {
        try {
            Scanner sc = new Scanner(System.in);
            int number = sc.nextInt();
            if (number < 0) {
                throw new ConsoleUserInputException("Ошибка: число не может быть отрицательным.");
            }
            else {
                return number;
            }
        } catch (InputMismatchException e) {
            throw new ConsoleUserInputException("Ошибка: введите целое число.");
        } catch (NoSuchElementException e) {
            throw new ConsoleUserInputException("Ошибка: нет данных для чтения.");
        } catch (IllegalStateException e) {
            throw new ConsoleUserInputException("Ошибка: Scanner закрыт.");
        }
    }

    /**
     * Обрабатывает выбор пункта меню пользователем и возвращает номер выбранного пункта.
     * @param itemsCount количество пунктов в меню.
     * @return номер выбранного пункта.
     * @throws ConsoleUserInputException если произошла ошибка при вводе.
     */
    public int chooseMenuItem(int itemsCount) throws ConsoleUserInputException {
        try {
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            if (choice < 0) {
                throw new ConsoleUserInputException("Ошибка: вводимое число не может быть отрицательным.");
            }
            else if (choice > itemsCount) {
                throw new ConsoleUserInputException("Ошибка: введите число от 0 до " + itemsCount);
            }
            else {
                return choice;
            }
        } catch (InputMismatchException e) {
            throw new ConsoleUserInputException("Ошибка: введите целое число.", e);
        } catch (NoSuchElementException e) {
            throw new ConsoleUserInputException("Ошибка: нет данных для чтения.", e);
        } catch (IllegalStateException e) {
            throw new ConsoleUserInputException("Ошибка: Scanner закрыт.", e);
        }
    }

    /**
     * Составляет текст консольного меню для выбора типа рабочего пространства.
     * @param types список типов рабочего пространства.
     * @return текст консольного меню.
     */
    public static String showWorkspaceTypesDirectoryMenu(ArrayList<WorkspaceTypes> types) {
        StringBuilder sb = new StringBuilder();
        sb.append("Выберите тип рабочего пространства: \n");
        int rowNumber = 1;
        for (WorkspaceTypes type : types) {
            sb.append(rowNumber).append(") ").append(type.getName()).append("\n");
            rowNumber++;
        }
        sb.append("0 - Назад");
        return sb.toString();
    }

    /**
     * Составляет текст консольного меню для выбора статуса брони.
     * @param statuses список статусов брони.
     * @return текст консольного меню.
     */
    public static String showBookingStatusesDirectoryMenu(ArrayList<BookingStatuses> statuses) {
        StringBuilder sb = new StringBuilder();
        sb.append("Выберите статус брони: \n");
        int rowNumber = 1;
        for (BookingStatuses status : statuses) {
            sb.append(rowNumber).append(") ").append(status.getName()).append("\n");
            rowNumber++;
        }
        sb.append("0 - Назад");
        return sb.toString();
    }
}
