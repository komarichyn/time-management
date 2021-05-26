package com.jc.tm.ui;

import com.jc.tm.ui.console.MyConsole;
import com.jc.tm.ui.console.MyDevice;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskConsole {
  MyDevice console = MyConsole.defaultTextDevice();
  AtomicBoolean start = new AtomicBoolean(Boolean.TRUE);
  
  public void start(){
    console.clear();
    while (start.get()) {
      drawMenu();
      String menuNum = console.readLine("choose menu number...");
      console.clear();
//      console.printf("you choose %s%n", menuNum);
      chooseMainMenu(menuNum);
    }
//    drawMenu();
  }

  //menu
  private void drawMenu() {
    console.printf("-------MENU TASK-------%n");
    console.printf("  1. Create task%n");
    console.printf("  2. Update task%n");
    console.printf("  3. Pull task by id%n");
    console.printf("  4. Pull all tasks%n");
    console.printf("  5. Delete task%n");
    console.printf("  0. Exit%n");
  }

  private void chooseMainMenu(String numberStr){
    switch (numberStr){
      case "1" : {
        break;
      }
      case "2": {
        break;
      }
      case "0" :{
        console.clear();
        console.printf("Okay. see you later!%n");
        start.compareAndSet(true, false);
        break;
      }
      default:{
        console.printf("wrong data, please try again%n");
      }
    }
  }
}
