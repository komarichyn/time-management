package com.jc.tm.ui.console;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public abstract class MyDevice {
  public abstract MyDevice printf(String fmt, Object... params)
      throws ConsoleException;

  public abstract String readLine() throws ConsoleException;
  public abstract String readLine(String fmt, Object ...params) throws ConsoleException;

  public abstract char[] readPassword() throws ConsoleException;

  public abstract Reader reader() throws ConsoleException;

  public abstract PrintWriter writer() throws ConsoleException;

  public void clear() throws ConsoleException {
    try {
      final String os = System.getProperty("os.name");
      if (os.contains("Windows")) {
        Runtime.getRuntime().exec("cls");
      } else {
        Runtime.getRuntime().exec("clear");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
