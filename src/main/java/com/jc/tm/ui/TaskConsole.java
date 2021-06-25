package com.jc.tm.ui;

import com.jc.tm.service.ITaskService;
import com.jc.tm.ui.console.MyConsole;
import com.jc.tm.ui.console.MyDevice;
import com.jc.tm.ui.subMenu.TaskSubMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TaskConsole {

  MyDevice console = MyConsole.defaultTextDevice();
  AtomicBoolean start = new AtomicBoolean(Boolean.TRUE);
  private ITaskService service;
  private TaskSubMenu taskSubMenu;
  private int programStart = 0;
  int page = 0;


  public TaskConsole(@Autowired ITaskService service) {
    this.service = service;
    taskSubMenu = new TaskSubMenu(console, service);
  }

  public void launch() {
    console.clear();
    while (start.get()) {
      if (programStart > 0) {
        drawMenu();
        chooseTaskMenu(console.readLine("Choose menu number: "));
      } else {
        if (taskSubMenu.getFiveDueDateTasks(page) == 5) {
          programControlKeys();
          String userChoose = console.readLine("Your choose ");
          console.clear();
          userChoose(userChoose);
          programStart++;
        } else {
          drawMenu();
          chooseTaskMenu(console.readLine("Choose menu number: "));
        }
      }
    }
  }

  //menu
  private void drawMenu() {
    console.printf("%n-------MENU-------%n");
    console.printf("  1. Create task%n");
    console.printf("  2. Update task%n");
    console.printf("  3. Pull task by id%n");
    console.printf("  4. Pull all tasks%n");
    console.printf("  5. Delete task%n");
    console.printf("  9. Exit%n");
  }

  private void programControlKeys() {
    console.printf("Keys to use program: " +
        "> - next five tasks, " +
        "< - previous five tasks, " +
        "2 - menu, " +
        "9 - exit%n");
  }

  private void drawUpdateTaskMenu() {
    console.printf("-------MENU UPDATE TASK-------%n");
    console.printf("  1. Update name%n");
    console.printf("  2. Update description%n");
    console.printf("  3. Update date%n");
    console.printf("  4. Update status%n");
    console.printf("  5. Back%n");
  }

  private void userChoose(String numberStr) {
    switch (numberStr) {
      case "<": {
        if (page == 0) {
          System.err.println("First page");
          programStart--;
          break;
        }
//                console.clear();
        taskSubMenu.getFiveDueDateTasks(page);
        console.clear();
        page = page - 5;
        programStart--;
        break;
      }
      case ">": {
//                console.clear();
        page = page + 5;
//                taskSubMenu.getFiveDueDateTasks(page);
        console.clear();
        programStart--;
        break;
      }
      case "2": {
        console.clear();
        drawMenu();
        page = 0;
        chooseTaskMenu(console.readLine("Choose menu number: "));
        break;
      }
      case "9": {
        console.clear();
        console.printf("Okay. see you later!%n");
        start.compareAndSet(true, false);
        break;
      }
      default: {
        console.clear();
        console.printf("Wrong data, please try again%n");
        programStart--;
      }
    }
  }

  private void chooseTaskMenu(String numberStr) {
    switch (numberStr) {
      case "1": {
        taskSubMenu.createTask();
        break;
      }
      case "2": {
        taskSubMenu.updateTask();
        break;
      }
      case "3": {
        taskSubMenu.getByIdTask();
        console.readLine();
        console.clear();
        break;
      }
      case "4": {
        console.clear();
        taskSubMenu.getFiveDueDateTasks(page);
        programControlKeys();
        userChoose(console.readLine("Your choose 2 "));
        break;
      }
      case "5": {
        taskSubMenu.removeTask();
        console.readLine();
        console.clear();
        break;
      }
      case "9": {
        console.clear();
        console.printf("Okay. see you later!%n");
        start.compareAndSet(true, false);
        break;
      }
      default: {
        console.printf("Wrong data, please try again%n");
        console.readLine();
        console.clear();
      }
    }
  }
}
