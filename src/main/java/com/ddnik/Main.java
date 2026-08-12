package com.ddnik;

import com.ddnik.controller.ConsoleReader;
import com.ddnik.controller.MainController;

public class Main {

    public static void main(String[] args) {
        MainController main = new MainController();
        main.start();
        ConsoleReader.cls();
        System.out.println("Вы завершили работу с программой.");
        System.exit(0);
    }
}