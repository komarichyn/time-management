package com.jc.tm.ui.console;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MyConsole {
  private MyConsole() {}

  private static MyDevice DEFAULT = (System.console() == null) ? streamDevice(
      System.in, System.out)
      : new ConsoleDevice(System.console());

  /**
   * The default system text I/O device.
   *
   * @return the default device
   */
  public static MyDevice defaultTextDevice() {
    return DEFAULT;
  }

  /**
   * Returns a text I/O device wrapping the given streams. The default system
   * encoding is used to decode/encode data.
   *
   * @param in
   *          an input source
   * @param out
   *          an output target
   * @return a new device
   */
  public static MyDevice streamDevice(InputStream in, OutputStream out) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    PrintWriter writer = new PrintWriter(out, true);
    return new CharacterDevice(reader, writer);
  }

  /**
   * Returns a text I/O device wrapping the given streams.
   *
   * @param reader
   *          an input source
   * @param writer
   *          an output target
   * @return a new device
   */
  public static MyDevice characterDevice(BufferedReader reader,
      PrintWriter writer) {
    return new CharacterDevice(reader, writer);
  }
}
