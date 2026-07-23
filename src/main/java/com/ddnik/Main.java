package com.ddnik;

import com.ddnik.controller.AuthController;
import com.ddnik.controller.MainController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        MainController.start();
        Menu.cls();
        System.out.println("Вы завершили работу с программой.");
        System.exit(0);
    }
}