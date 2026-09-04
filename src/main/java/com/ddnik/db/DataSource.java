package com.ddnik.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static HikariConfig conf = new HikariConfig("db.properties");
    private static HikariDataSource ds = new HikariDataSource(conf);

    public DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static HikariDataSource getDs() {
        return ds;
    }
}
