package com.ddnik.db;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.sql.SQLException;

public final class MigrationDB {

    private MigrationDB() {
    }

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
