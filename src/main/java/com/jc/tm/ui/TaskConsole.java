package com.jc.tm.ui;

import com.jc.tm.service.ITaskService;
import com.jc.tm.ui.subMenu.CommentSubMenu;
import com.jc.tm.ui.console.MyConsole;
import com.jc.tm.ui.console.MyDevice;
import com.jc.tm.ui.subMenu.TaskSubMenu;

import java.util.concurrent.atomic.AtomicBoolean;

public class TaskConsole {
    MyDevice console = MyConsole.defaultTextDevice();
    AtomicBoolean start = new AtomicBoolean(Boolean.TRUE);
    private ITaskService service;
    private CommentSubMenu commentSubMenu;
    private TaskSubMenu taskSubMenu;

    public TaskConsole(ITaskService service) {
        this.service = service;
        commentSubMenu = new CommentSubMenu(console,service);
        taskSubMenu = new TaskSubMenu(console, service);
    }

    public void start() {
//        console.clear(); TODO later I uncomment this string
        while (start.get()) {
            drawMenu();
            String menuNum = console.readLine("choose menu number...");
//            console.clear(); TODO later I uncomment this string
            console.printf("you choose %s%n", menuNum);
            chooseMainMenu(menuNum);
        }
        // drawMenu();
    }

    //menu
    private void drawMenu() {
        console.printf("-------WELCOME-------%n");
        console.printf("-------APP MENU-------%n");
        console.printf("  1. Task menu%n");
        console.printf("  2. Comment menu%n");
        console.printf("  0. Exit%n");
    }

    private void drawSubMenuTask() {
        console.printf("-------MENU TASK-------%n");
        console.printf("  1. Create task%n");
        console.printf("  2. Update task%n");
        console.printf("  3. Pull task by id%n");
        console.printf("  4. Pull all tasks%n");
        console.printf("  5. Delete task%n");
        console.printf("  0. Back%n");
    }

    private void drawUpdateTaskMenu() {
        console.printf("-------MENU UPDATE TASK-------%n");
        console.printf("  1. Update name%n");
        console.printf("  2. Update description%n");
        console.printf("  3. Update date%n");
        console.printf("  4. Update status%n");
        console.printf("  5. Back%n");
    }

    private void drawSubMenuComment() {
        console.printf("-------MENU COMMENT-------%n");
        console.printf("  1. Create comment%n");
        console.printf("  2. Update comment%n");
        console.printf("  3. Pull comment by id%n");
        console.printf("  4. Pull all comments%n");
        console.printf("  5. Delete comment%n");
        console.printf("  0. Back%n");
    }

    private void chooseMainMenu(String numberStr) {
        switch (numberStr) {
            case "1": {
//                console.clear(); TODO later I uncomment this string
                drawSubMenuTask();
                chooseTaskSubMenu(console.readLine("choose menu number..."));
                break;
            }
            case "2": {
//                console.clear(); TODO later I uncomment this string
                drawSubMenuComment();
                chooseCommentSubMenu(console.readLine("choose menu number..."));
                break;
            }
            case "0": {
//                console.clear(); TODO later I uncomment this string
                console.printf("Okay. see you later!%n");
                start.compareAndSet(true, false);
                break;
            }
            default: {
                console.printf("wrong data, please try again%n");
            }
        }
    }

    private void chooseTaskSubMenu(String numberStr) {
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
                taskSubMenu.getAllTask();
                break;
            }
            case "5": {
                taskSubMenu.removeTask();
                break;
            }
            case "0": {
//                console.clear(); TODO later I uncomment this string
                drawMenu();
                chooseMainMenu(console.readLine("choose menu number..."));
                break;
            }
            default: {
                console.printf("wrong data, please try again%n");
            }
        }
    }

    private void chooseCommentSubMenu(String numberStr) {
        switch (numberStr) {
            case "1": {
                commentSubMenu.createComment();
                break;
            }
            case "2": {
                commentSubMenu.updateComment();
                break;
            }
            case "0": {
                console.clear();
                drawMenu();
                chooseMainMenu(console.readLine("choose menu number..."));
                break;
            }
            default: {
                console.printf("wrong data, please try again%n");
            }
        }
    }

}
