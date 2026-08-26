package com.ddnik;

import com.ddnik.controller.ConsoleReader;
import com.ddnik.controller.MainController;
import com.ddnik.controller.Out;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            logger.error("Необработанное исключение в потоке '{}'", thread.getName(), throwable);

            Out.printlnRedBack("Произошла непредвиденная ошибка. Работа программы будет прекращена.");
        });

        MainController main = new MainController();
        main.start();
        ConsoleReader.cls();
        Out.printlnBlueBack("Вы завершили работу с программой.");
        System.exit(0);
    }
}