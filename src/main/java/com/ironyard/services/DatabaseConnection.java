package com.ironyard.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by nathanielellsworth on 10/11/16.
 */
public class DatabaseConnection {
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres","admin");
    }
}
