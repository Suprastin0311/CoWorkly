package com.ddnik;

import com.ddnik.controller.MainController;

public class Main {

    public static void main(String[] args) {
        MainController main = new MainController();
        main.start();
        Menu.cls();
        System.out.println("Вы завершили работу с программой.");
        System.exit(0);
    }
}