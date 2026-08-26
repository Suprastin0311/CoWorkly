package com.ddnik.controller;

import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Optional;
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

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    private static final Logger logger = LoggerFactory.getLogger(ConsoleReader.class);

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
     * Кроссплатформенный способ очистки консоли.
     */
    public static void cls() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J\033[3J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Ожидает ввода любого символа для продолжения.
     */
    public static void waitInput()  {
        try {
            Out.println("Нажмите любую клавишу чтобы продолжить...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
        } catch (NoSuchElementException e) {
            Out.printlnRed("Ошибка: нет данных для чтения.");
            logger.error("Ошибка ввода.", e);
        } catch (IllegalStateException e) {
            Out.printlnRed("Ошибка: консоль не отвечает.");
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
    public static int chooseMenuItem(int minItem, int maxItem) {
        while (true) {
            try {
                Out.print("> ");
                Optional<Integer> choice = inputInteger();

                if (choice.isPresent()) {
                    if (choice.get() < minItem || choice.get() > maxItem)
                        throw new ConsoleUserInputException("Ошибка: данного пункта не существует в меню.");
                    else return choice.get();
                }
                else Out.printlnRed("Ошибка: не удалось прочитать введённое число.");
            } catch (ConsoleUserInputException e) {
                Out.printlnRed(e.getMessage());
            }
        }
    }

    /**
     * Читает введённый строку с email из консоли.
     * @return введённая строка.
     * <br>Возвращает {@link Optional#empty()}, если пользователь ввёл символ <code>q</code> и хочет прекратить ввод
     */
    public static Optional<String> readEmail() {
        while (true) {
            Optional<String> email = readString("Email");
            if (email.isPresent())
                if (validateEmail(email.get())) return email;
                else Out.printlnRed("Email не соответствует шаблону example@mail.domen");
            else return Optional.empty();
        }
    }

    /**
     * Читает введённую строку из консоли.
     * @param message сообщение, выводимое в строке ввода.<br>
     *                Пример: Введите вместимость: [ввод]
     * @return введённая строка.
     * <br>Возвращает {@link Optional#empty()}, если пользователь ввёл символ <code>q</code> и хочет прекратить ввод
     */
    public static Optional<String> readString(String message) {
        while (true) {
            try {
                Out.print(message + ": ");
                String input = inputString();
                if (input.equals("q")) return Optional.empty();
                else return Optional.of(input);
            } catch (ConsoleUserInputException e) {
                Out.printlnRed(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Циклично читает положительное целое число из консоли.
     * @param message сообщение, выводимое в строке ввода.<br>
     *                Пример: Введите вместимость: [ввод]
     * @return ведённое положительное целое число.
     * <br>Возвращает {@link Optional#empty()}, если пользователь ввёл символ <code>q</code> и хочет прекратить ввод
     */
    public static Optional<Integer> readPositiveInt(String message) {
        while (true) {
            try {
                Out.print(message + ": ");
                Optional<Integer> number = inputInteger();
                if (number.isEmpty()) return Optional.empty();
                else if (number.get() < 0) Out.printlnRed("Ошибка: число не должно быть отрицательным.");
                else return number;
            } catch (ConsoleUserInputException e) {
                Out.printlnRed(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Циклично читает целое число в пределах указанного диапазона.
     * @param message сообщение, выводимое в строке ввода без указания диапазона.<br>
     *                Пример: "Введите вместимость", но не "Введите вместимость от, до"
     * @param min левая граница диапазона.
     * @param max правая граница диапазона.
     * @return введённое положительное число.
     * <br>Возвращает {@link Optional#empty()}, если пользователь ввёл символ <code>q</code> и хочет прекратить ввод
     */
    public static Optional<Integer> readIntInRange(String message, int min, int max) {
        while(true) {
            try {
                Out.print(String.format("%s в пределах от %d до %d: ", message, min, max));
                Optional<Integer> number = inputInteger();
                if (number.isEmpty()) return Optional.empty();
                else if (number.get() < min || number.get() > max)
                    Out.printlnRed("Ошибка: введено число вне допустимых значений.");
                else return number;
            } catch (ConsoleUserInputException e) {
                Out.printlnRed(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Циклично читает вещественное число из консоли.
     * @param message сообщение, выводимое в строке ввода.<br>
     *                Пример: Введите вместимость: [ввод]
     * @return введённое вещественное число
     * <br>Возвращает {@link Optional#empty()}, если пользователь ввёл символ <code>q</code> и хочет прекратить ввод
     */
    public static Optional<Double> readPositiveDouble(String message) {
        while (true) {
            try {
                Out.print(message + ": ");
                Optional<Double> number = inputDouble();
                if (number.isEmpty()) return Optional.empty();
                else if (number.get() < 0.0) Out.printlnRed("Ошибка: число не должно быть отрицательным.");
                else return number;
            } catch (ConsoleUserInputException e) {
                Out.printlnRed(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Циклично читает BigDecimal число из консоли.
     * @param message сообщение, выводимое в строке ввода.<br>
     *                Пример: Введите вместимость: [ввод]
     * @return введённое BigDecimal число
     * <br>Возвращает {@link Optional#empty()}, если пользователь ввёл символ <code>q</code> и хочет прекратить ввод
     */
    public static Optional<BigDecimal> readPositiveBigDecimal(String message) {
        while (true) {
            try {
                Optional<Double> number = readPositiveDouble(message);
                return number.map(BigDecimal::valueOf);
            } catch (NumberFormatException e) {
                logger.error("Ошибка при вводе значения BigDecimal.", e);
                Out.printlnRed("Ошибка: введите вещественное значение.");
            } catch (NullPointerException e) {
                logger.error("Ошибка при вводе значения BigDecimal.", e);
                Out.printlnRed("Ошибка: непредвиденная ошибка.");
            }
        }
    }

    /**
     * Читает дату из консоли.
     * @param message сообщение, выводимое в строке ввода.<br>
     *                Пример: Введите вместимость: [ввод]
     * @return введённая дата
     * <br>Возвращает {@link Optional#empty()}, если пользователь ввёл символ <code>q</code> и хочет прекратить ввод
     */
    public static Optional<Date> readDate(String message) {
        while (true) {
            try {
                Out.print(message + ": ");
                return inputDate();
            } catch (ConsoleUserInputException e) {
                Out.printlnRed(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Считывает из консоли введённую строку.
     * @return введённая строка.
     */
    private static String inputString() throws ConsoleUserInputException {
        try {
            String input = new Scanner(System.in).nextLine().trim();
            if (input.isBlank()) throw new ConsoleUserInputException("Ошибка: введена пустая строка.");
            else return input;
        } catch (NoSuchElementException e) {
            throw new ConsoleUserInputException("Ошибка: нет данных для чтения.", e);
        } catch (IllegalStateException e) {
            throw new ConsoleUserInputException("Ошибка: консоль не отвечает.", e);
        }
    }

    /**
     * Считывает из консоли вещественное число и обрабатывает исключения.
     * @return введённое из консоли вещественное число.
     * @throws ConsoleUserInputException в случае ошибки ввода.
     * <br>Возвращает {@link Optional#empty()}, если пользователь ввёл символ <code>q</code> и хочет прекратить ввод
     */
    private static Optional<Double> inputDouble() throws ConsoleUserInputException {
        try {
            String input = inputString();
            if (input.equals("q")) return Optional.empty();
            return Optional.of(Double.valueOf(input));
        } catch (NullPointerException e) {
            logger.error("Ошибка при вводе даты", e);
            throw new ConsoleUserInputException("Ошибка: не удалось прочитать вещественное число.", e);
        } catch (NumberFormatException e) {
            logger.error("Ошибка при вводе вещественного числа", e);
            throw new ConsoleUserInputException("Ошибка: введите вещественное число.", e);
        }
    }

    /**
     * Считывает из консоли дату и обрабатывает исключения.
     * @return введённая из консоли дата.
     * @throws ConsoleUserInputException в случае ошибки ввода.
     */
    private static Optional<Date> inputDate() throws ConsoleUserInputException {
        try {
            String input = inputString();
            if (input.equals("q")) return Optional.empty();
            return Optional.of(Date.valueOf(LocalDate.parse(input, DATE_TIME_FORMATTER)));
        } catch (NullPointerException e) {
            logger.error("Ошибка при вводе даты", e);
            throw new ConsoleUserInputException("Ошибка: не удалось прочитать дату.", e);
        } catch (DateTimeParseException e) {
            logger.error("Ошибка при вводе даты", e);
            throw new ConsoleUserInputException("Ошибка: введённое значение не соответствует формату [гггг.мм.дд] или недопустимые значения.", e);
        }
    }

    /**
     * Считывает из консоли целое число и обрабатывает исключения.
     * @return введённое из консоли целое число.
     * @throws ConsoleUserInputException в случае ошибки ввода.
     */
    private static Optional<Integer> inputInteger() throws ConsoleUserInputException {
        try {
            String input = inputString();
            if (input.equals("q")) return Optional.empty();
            return Optional.of(Integer.parseInt(input));
        } catch (NullPointerException e) {
            logger.error("Ошибка при вводе даты", e);
            throw new ConsoleUserInputException("Ошибка: не удалось прочитать целое число.", e);
        } catch (NumberFormatException e) {
            logger.error("Ошибка при вводе целого числа", e);
            throw new ConsoleUserInputException("Ошибка: введите целое число.", e);
        }
    }
}
