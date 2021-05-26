package com.jc.tm.ui.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public class CharacterDevice extends MyDevice {

  private final BufferedReader reader;
  private final PrintWriter writer;

  public CharacterDevice(BufferedReader reader, PrintWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  @Override
  public CharacterDevice printf(String fmt, Object... params)
      throws ConsoleException {
    writer.printf(fmt, params);
    return this;
  }

  @Override
  public String readLine() throws ConsoleException {
    try {
      return reader.readLine();
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public String readLine(String fmt, Object ... params) throws ConsoleException {
    return this.printf(fmt, params).readLine();
  }

  @Override
  public char[] readPassword() throws ConsoleException {
    return readLine().toCharArray();
  }

  @Override
  public Reader reader() throws ConsoleException {
    return reader;
  }

  @Override
  public PrintWriter writer() throws ConsoleException {
    return writer;
  }

  @Override
  public void clear() throws ConsoleException {
    super.clear();
    writer().print("\r");
    writer().print("\033[H\033[2J");
    writer().flush();
  }
}
