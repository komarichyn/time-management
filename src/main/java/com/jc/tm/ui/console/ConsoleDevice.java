package com.jc.tm.ui.console;

import java.io.Console;
import java.io.PrintWriter;
import java.io.Reader;

public class ConsoleDevice extends MyDevice {

  private final Console console;

  public ConsoleDevice(Console console) {
    this.console = console;
  }

  @Override
  public MyDevice printf(String fmt, Object... params)
      throws ConsoleException {
    console.format(fmt, params);
    return this;
  }

  @Override
  public Reader reader() throws ConsoleException {
    return console.reader();
  }

  @Override
  public String readLine() throws ConsoleException {
    return console.readLine();
  }

  @Override
  public String readLine(String fmt, Object... params) throws ConsoleException {
    return console.readLine(fmt, params);
  }

  @Override
  public char[] readPassword() throws ConsoleException {
    return console.readPassword();
  }

  @Override
  public PrintWriter writer() throws ConsoleException {
    return console.writer();
  }

  @Override
  public void clear() throws ConsoleException {
    super.clear();
    console.writer().print("\r");
    console.writer().print("\033[H\033[2J");
    console.writer().flush();
  }
}
