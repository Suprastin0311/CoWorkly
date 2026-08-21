package com.ddnik.controller;

import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Содержит константы и методы работы с консолью.
 */
public class ConsoleReader {

    /**
     * Константа с маской для ввода email
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_DATE_REGEX =
            Pattern.compile("^[0-9]+\\.[0-9]+\\.[0-9]$", Pattern.CASE_INSENSITIVE);
    private static final Logger logger = LoggerFactory.getLogger(ConsoleReader.class);

    /**
     * Меню авторизации
     * @param menuName название текстового файла с меню
     */
    public static void printMenu(String menuName) {
        String resourcePath = "menu/" + menuName + ".txt";

        try (InputStream is = ClassLoader.getSystemResourceAsStream(resourcePath)) {
            if (is == null) {
                System.out.println("Файл не найден: " + resourcePath);
                return;
            }

            // Вывод файла построчно в консоль
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                br.lines().forEach(System.out::println);

                System.out.print("> ");
            }

        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл " + resourcePath + ": " + e.getLocalizedMessage());
        }
    }

    /**
     * Валидация Email
     * @param emailStr строка, содержащая email
     * @return результат валидации: <br>
     * true - email совпадает с маской <br>
     * false - email не совпадает с маской
     */
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    /**
     * Валидация строкового представления даты.
     * @param dateStr строковое представление даты
     * @return результат валидации<br>
     * true - дата совпадает с маской<br>
     * false - дата не совпадает с маской
     */
    public static boolean validateDate(String dateStr) {
        Matcher matcher = VALID_DATE_REGEX.matcher(dateStr);
        return matcher.matches();
    }

    /**
     * Кроссплатформенный способ очистки консоли.
     */
    public static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Ожидает ввода любого символа для продолжения.
     */
    public static void waitInput()  {
        try {
            System.out.print("Нажмите любую клавишу чтобы продолжить...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка: нет данных для чтения.");
            logger.error("Ошибка ввода.", e);
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: консоль не отвечает.");
            logger.error("Ошибка ввода.", e);
        }
    }

    /**
     * Обрабатывает выбор пункта меню пользователем и возвращает номер выбранного пункта.
     * @param minItem минимальный номер пункта меню.
     * @param maxItem максимальный номер пункта меню.
     * @return номер выбранного пункта.
     * @throws ConsoleUserInputException если произошла ошибка при вводе.
     */
    public static int chooseMenuItem(int minItem, int maxItem) throws ConsoleUserInputException {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("> ");
            int choice = sc.nextInt();

            if (choice < 0) {
                throw new ConsoleUserInputException("Ошибка: вводимое число не может быть отрицательным.");
            }
            else if (choice < minItem || choice > maxItem) {
                throw new ConsoleUserInputException("Ошибка: данного пункта не существует в меню.");
            }
            else {
                return choice;
            }
        } catch (InputMismatchException e) {
            throw new ConsoleUserInputException("Ошибка: введите целое число для выбора пункта меню.", e);
        } catch (NoSuchElementException e) {
            throw new ConsoleUserInputException("Ошибка: нет данных для чтения.", e);
        } catch (IllegalStateException e) {
            throw new ConsoleUserInputException("Ошибка: консоль не отвечает.", e);
        }
    }

    /**
     * Читает введённую строку из консоли.
     * @return введённая строка.
     * @throws ConsoleUserInputException если произошла ошибка при вводе.
     */
    public static String readString() throws ConsoleUserInputException {
        try {
            return new Scanner(System.in).nextLine().trim();
        } catch (NoSuchElementException e) {
            throw new ConsoleUserInputException("Ошибка: нет данных для чтения.", e);
        } catch (IllegalStateException e) {
            throw new ConsoleUserInputException("Ошибка: консоль не отвечает.", e);
        }
    }

    /**
     * Читает положительное целое число из консоли.
     * @return ведённое положительное целое число
     * @throws ConsoleUserInputException если произошла ошибка при вводе.
     */
    public static int readPositiveInt() throws ConsoleUserInputException {
        try {
            Scanner sc = new Scanner(System.in);
            int number = sc.nextInt();
            if (number < 0) {
                throw new ConsoleUserInputException("Ошибка: число не может быть отрицательным.");
            }
            return number;
        } catch (InputMismatchException e) {
            throw new ConsoleUserInputException("Ошибка: введите целое число.", e);
        } catch (NoSuchElementException e) {
            throw new ConsoleUserInputException("Ошибка: нет данных для чтения.", e);
        } catch (IllegalStateException e) {
            throw new ConsoleUserInputException("Ошибка: консоль не отвечает.", e);
        }
    }

    /**
     * Читает вещественное число из консоли.
     * @return введённое вещественное число
     * @throws ConsoleUserInputException если произошла ошибка при вводе.
     */
    public static double readDouble() throws ConsoleUserInputException {
        try {
            return new Scanner(System.in).nextDouble();
        } catch (InputMismatchException e) {
            throw new ConsoleUserInputException("Ошибка: введите число.", e);
        } catch (NoSuchElementException e) {
            throw new ConsoleUserInputException("Ошибка: нет данных для чтения.", e);
        } catch (IllegalStateException e) {
            throw new ConsoleUserInputException("Ошибка: консоль не отвечает.", e);
        }
    }



    /**
     * Читает дату из консоли
     * @return дата
     * @throws ConsoleUserInputException если произошла ошибка при вводе
     */
    public static Date readDate() throws ConsoleUserInputException {
        String dateStr = "";
        try {
            System.out.print("Введите дату (гггг.мм.дд.): ");
            dateStr = readString();
            if (dateStr.isEmpty()) {
                throw new ConsoleUserInputException("Ошибка: введена пустая строка.");
            }
            else if (!validateDate(dateStr)) {
                throw new ConsoleUserInputException("Ошибка: введённое значение не соответствует формату гггг.мм.дд.");
            }
        } catch (ConsoleUserInputException e) {
            System.out.println(e.getMessage());
        }
        finally {
            return Date.valueOf(dateStr);
        }
    }


}
