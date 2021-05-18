package com.jc.tm.database.dao;

import com.jc.tm.helper.LoadPropertiesHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Jdbc {
    static Logger LOGGER = Logger.getLogger(Jdbc.class.getName());

    public static Connection getConnection() {
        Connection connection = null;
        var propertiesHelper = LoadPropertiesHelper.getInstance();
        var properties = propertiesHelper.loadProperties();
        String host = properties.getProperty("db.host");
        String port = properties.getProperty("db.port");
        String name = properties.getProperty("db.name");
        String user = properties.getProperty("db.user");
        String pass = properties.getProperty("db.pass");

        try {
            connection = DriverManager.getConnection(getUrl(host, port, name), user, pass);
            System.out.println("Connected with database " + name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private static String getUrl(String host, String port, String dbName) {
        StringBuilder builder = new StringBuilder();
        builder.append("jdbc:mysql://");
        builder.append(host);
        builder.append(":");
        builder.append(port);
        builder.append("/");
        builder.append(dbName);
        return builder.toString();
    }

    public static void closeConnection(Connection connection){
        try {
            if (connection != null) {
                connection.close();
                if (connection.isClosed()) {
                    LOGGER.info("Connection closed");
                } else {
                    LOGGER.info("Connection not closed");
                }
            }
        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, "Exception", throwables);
        }
    }
}
