package com.ddnik;

import com.ddnik.controller.AuthController;
import com.ddnik.controller.MainController;

public class Main {

    public static void main(String[] args) {
        MainController.start();
        Menu.cls();
        System.out.println("Вы завершили работу с программой.");
        System.exit(0);
    }
}