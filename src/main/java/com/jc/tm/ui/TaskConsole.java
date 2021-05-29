package com.jc.tm.ui;

import com.jc.tm.ui.console.MyConsole;
import com.jc.tm.ui.console.MyDevice;

import java.util.concurrent.atomic.AtomicBoolean;

public class TaskConsole {
    MyDevice console = MyConsole.defaultTextDevice();
    AtomicBoolean start = new AtomicBoolean(Boolean.TRUE);

    public void start() {
        console.clear();
        while (start.get()) {
            drawMenu();
            String menuNum = console.readLine("choose menu number...");
            console.clear();
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
                console.clear();
                drawSubMenuTask();
                chooseTaskSubMenu(console.readLine("choose menu number..."));
                break;
            }
            case "2": {
                console.clear();
                drawSubMenuComment();
                chooseCommentSubMenu(console.readLine("choose menu number..."));
                break;
            }
            case "0": {
                console.clear();
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
                break;
            }
            case "2": {

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

    private void chooseCommentSubMenu(String numberStr) {
        switch (numberStr) {
            case "1": {
                break;
            }
            case "2": {
                break;
            }
            case "0": {
                console.clear();
                console.clear();
                drawMenu();
                chooseMainMenu(console.readLine());
                break;
            }
            default: {
                console.printf("wrong data, please try again%n");
            }
        }
    }

}
