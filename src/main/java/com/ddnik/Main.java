package com.ddnik;

import com.ddnik.controller.ConsoleReader;
import com.ddnik.controller.MainController;
import com.ddnik.controller.Out;
import com.ddnik.db.DataSource;
import com.ddnik.db.MigrationDB;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            logger.error("Необработанное исключение в потоке '{}'", thread.getName(), throwable);

            Out.printlnRedBack("Произошла непредвиденная ошибка. Работа программы будет прекращена.");
        });

        try {
            MigrationDB.migrate(DataSource.getDs());
        } catch (FlywayException e) {
            logger.error(e.getLocalizedMessage(), e);
            Out.printlnRed("Произошла ошибка в процессе актуализации базы данных, данные могут быть неактуальны.");
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage(), e);
            Out.printlnRedBack("Произошла ошибка на уровне базы данных. Работа программы будет прекращена.");
            System.exit(0);
        }

        MainController main = new MainController();
        main.start();
        ConsoleReader.cls();
        Out.printlnBlueBack("Вы завершили работу с программой.");
        System.exit(0);
    }
}