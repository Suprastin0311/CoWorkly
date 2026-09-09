package com.ddnik.db;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.sql.SQLException;

/**
 * Реализует миграцию базы данных.
 */
public final class MigrationDB {

    private MigrationDB() {
    }

    /**
     * Запуск миграции.
     * @param ds источник данных типа {@link HikariDataSource}.
     * @throws SQLException в случае ошибки на уровне базы данных.
     */
    public static void migrate(HikariDataSource ds) throws SQLException {
        Flyway flyway = Flyway.configure()
                .dataSource(ds)
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .validateOnMigrate(false)
                .outOfOrder(true)
                .load();

        flyway.migrate();
    }
}
