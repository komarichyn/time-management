package com.jc.tm.helper;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseHelper {

  private static DatabaseHelper helper;

  private DatabaseHelper() {
  }

  public static DatabaseHelper getInstance() {
    if (helper == null) {
      helper = new DatabaseHelper();
    }
    return helper;
  }


  public Connection getConnection() {
    Connection connection = null;
    var propertiesHelper = LoadPropertiesHelper.getInstance();
    var properties = propertiesHelper.loadProperties();
    String driver = properties.getProperty("db.driver-name");
    String user = properties.getProperty("db.user");
    String pass = properties.getProperty("db.password");
    String url = properties.getProperty("db.url");

    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(url, user, pass);
      log.info("open new connection");
    } catch (SQLException | ClassNotFoundException e) {
      log.error(e.getMessage(), e);
      return null;
    }
    return connection;
  }

  public void closeConnection(Connection connection) {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
        log.info("Connection closed");
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
  }


}
