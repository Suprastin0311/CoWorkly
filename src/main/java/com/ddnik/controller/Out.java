package com.ddnik.controller;

/**
 * Реализует статические методы цветного вывода текста в поток {@code System.out}.
 */
public class Out {
    private static String RESET = "\u001B[0m";

    private static String BLACK = "\u001B[30m";
    private static String RED = "\u001B[31m";
    private static String GREEN = "\u001B[32m";
    private static String YELLOW = "\u001B[33m";
    private static String BLUE = "\u001B[34m";
    private static String PURPLE = "\u001B[35m";
    private static String CYAN = "\u001B[36m";
    private static String WHITE = "\u001B[37m";

    private static String BLACK_BACKGROUND = "\u001B[40m";
    private static String RED_BACKGROUND = "\u001B[41m";
    private static String GREEN_BACKGROUND = "\u001B[42m";
    private static String YELLOW_BACKGROUND = "\u001B[43m";
    private static String BLUE_BACKGROUND = "\u001B[44m";
    private static String PURPLE_BACKGROUND = "\u001B[45m";
    private static String CYAN_BACKGROUND = "\u001B[46m";
    private static String WHITE_BACKGROUND = "\u001B[47m";

    public static void print(String msg) {
        System.out.print(msg);
    }
    public static void println(String msg) {
        System.out.println(msg);
    }

    public static void printlnBlack(String message) {
        System.out.println(BLACK + message + RESET);
    }
    public static void printlnRed(String message) {
        System.out.println(RED + message + RESET);
    }
    public static void printlnWhite(String message) {
        System.out.println(WHITE + message + RESET);
    }
    public static void printlnGreen(String message) {
        System.out.println(GREEN + message + RESET);
    }
    public static void printlnYellow(String message) {
        System.out.println(YELLOW + message + RESET);
    }
    public static void printlnBlue(String message) {
        System.out.println(BLUE + message + RESET);
    }
    public static void printlnPurple(String message) {
        System.out.println(PURPLE + message + RESET);
    }
    public static void printlnCyan(String message) {
        System.out.println(CYAN + message + RESET);
    }
    public static void printlnWhiteBack(String message) {
        System.out.println(WHITE + BLACK + message + RESET);
    }
    public static void printlnBlackBack(String message) {
        System.out.println(BLACK_BACKGROUND + WHITE + message + RESET);
    }
    public static void printlnRedBack(String message) {
        System.out.println(RED_BACKGROUND + WHITE + message + RESET);
    }
    public static void printlnGreenBack(String message) {
        System.out.println(GREEN_BACKGROUND + WHITE + message + RESET);
    }
    public static void printlnYellowBack(String message) {
        System.out.println(YELLOW_BACKGROUND + BLACK + message + RESET);
    }
    public static void printlnBlueBack(String message) {
        System.out.println(BLUE_BACKGROUND + WHITE + message + RESET);
    }
    public static void printlnPurpleBack(String message) {
        System.out.println(PURPLE_BACKGROUND + WHITE + message + RESET);
    }
    public static void printlnCyanBack(String message) {
        System.out.println(CYAN_BACKGROUND + WHITE + message + RESET);
    }

    public static void printBlack(String message) {
        System.out.print(BLACK + message + RESET);
    }
    public static void printRed(String message) {
        System.out.print(RED + message + RESET);
    }
    public static void printWhite(String message) {
        System.out.print(WHITE + message + RESET);
    }
    public static void printGreen(String message) {
        System.out.print(GREEN + message + RESET);
    }
    public static void printYellow(String message) {
        System.out.print(YELLOW + message + RESET);
    }
    public static void printBlue(String message) {
        System.out.print(BLUE + message + RESET);
    }
    public static void printPurple(String message) {
        System.out.print(PURPLE + message + RESET);
    }
    public static void printCyan(String message) {
        System.out.print(CYAN + message + RESET);
    }
    public static void printWhiteBack(String message) {
        System.out.print(WHITE_BACKGROUND + BLACK + message + RESET);
    }
    public static void printBlackBack(String message) {
        System.out.print(BLACK_BACKGROUND + WHITE + message + RESET);
    }
    public static void printRedBack(String message) {
        System.out.print(RED_BACKGROUND + WHITE + message + RESET);
    }
    public static void printWhiteBackBack(String message) {
        System.out.print(WHITE_BACKGROUND + WHITE + message + RESET);
    }
    public static void printGreenBack(String message) {
        System.out.print(GREEN_BACKGROUND + WHITE + message + RESET);
    }
    public static void printYellowBack(String message) {
        System.out.print(YELLOW_BACKGROUND + WHITE + message + RESET);
    }
    public static void printBlueBack(String message) {
        System.out.print(BLUE_BACKGROUND + WHITE + message + RESET);
    }
    public static void printPurpleBack(String message) {
        System.out.print(PURPLE_BACKGROUND + WHITE + message + RESET);
    }
    public static void printCyanBack(String message) {
        System.out.print(CYAN_BACKGROUND + WHITE + message + RESET);
    }
}
