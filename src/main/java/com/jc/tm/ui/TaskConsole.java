package com.jc.tm.ui;

import com.jc.tm.service.ITaskService;
import com.jc.tm.ui.console.MyConsole;
import com.jc.tm.ui.console.MyDevice;
import com.jc.tm.ui.subMenu.TaskSubMenu;

import java.util.concurrent.atomic.AtomicBoolean;

public class TaskConsole {
    MyDevice console = MyConsole.defaultTextDevice();
    AtomicBoolean start = new AtomicBoolean(Boolean.TRUE);
    private ITaskService service;
    private TaskSubMenu taskSubMenu;
    private int programStart = 0;
    int page = 0;

    public TaskConsole(ITaskService service) {
        this.service = service;
        taskSubMenu = new TaskSubMenu(console, service);
    }

    public void start() {
        console.clear();
        while (start.get()) {
            if (programStart > 0) {
                drawMenu();
                chooseTaskMenu(console.readLine("Choose menu number: "));
            } else {
                taskSubMenu.getByIdTask();
//                taskSubMenu.getAllTask(page);
                String userChoose = console.readLine("Your choose");
                console.clear();
                userChoose(userChoose);
                programStart++;
            }
            /*String menuNum = console.readLine("Keys:%n" +
                    "> - next five tasks%n" +
                    "< - previous five tasks%n" +
                    "2 - menu%n" +
                    "9 - exit");*/
            /*String userChoose = console.readLine("Your choose");
            console.clear();
            userChoose(userChoose);*/
        }
//         drawMenu();
    }

    //menu
    private void drawMenu() {
        console.printf("%n-------MENU-------%n");
        console.printf("  1. Create task%n");
        console.printf("  2. Update task%n");
        console.printf("  3. Pull task by id%n");
        console.printf("  4. Pull all tasks%n");
        console.printf("  5. Delete task%n");
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
                console.clear();
                taskSubMenu.getAllTask(page);
                page = page - 5;
                programStart--;
                break;
            }
            case ">": {
                console.clear();
                taskSubMenu.getAllTask(page);
                page = page + 5;
                programStart--;
                break;
            }
            case "2": {
                console.clear();
                drawMenu();
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
                break;
            }
            case "4": {
                taskSubMenu.getAllTask(page);
                userChoose(console.readLine("Your choose 2: "));
                break;
            }
            case "5": {
                taskSubMenu.removeTask();
                break;
            }
            default: {
                console.printf("Wrong data, please try again%n");
            }
        }
    }
}
