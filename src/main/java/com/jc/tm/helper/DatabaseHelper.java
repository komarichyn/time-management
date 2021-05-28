package com.jc.tm.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseHelper {

  private static final String _NAME = "db.driver-name";
  private static final String _USER = "db.user";
  private static final String _PWD = "db.password";
  private static final String _URL = "db.url";

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
    String driver = properties.getProperty(_NAME);
    String user = properties.getProperty(_USER);
    String pass = properties.getProperty(_PWD);
    String url = properties.getProperty(_URL);

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
