package com.jc.tm.database.dao;

import com.jc.tm.helper.LoadPropertiesHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//fixme remove unused import
import java.util.Properties;

public class Jdbc {
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
            //fixme add more detail for output
            System.err.println("Connected");
        } catch (SQLException e) {
            //fixme do not use printstacktrace, it is not usefull to read infroamtion from console
            e.printStackTrace();
            //fixme add more detail for output
            System.err.println("Not connected");
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
}
