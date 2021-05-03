package com.jc.tm.database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc {
    public static final String URL = "jdbc:mysql://localhost:3306/timemanagement";
    public static final String USER = "root";
    public static final String PASSWORD = "mamaI533107";

    //method database connection
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.err.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Not connected");
        }
        return connection;
    }
}
