package com.jc.tm.userInterface;

import com.jc.tm.database.dao.CommentDaoImpl;
import com.jc.tm.database.dao.TaskDaoImpl;
import lombok.extern.slf4j.Slf4j;
import java.util.Scanner;

@Slf4j
public class ConsoleApplication {
    private CommentConsoleMenu commentConsoleMenu;
    private TaskConsoleMenu taskConsoleMenu;
    private Scanner sc;

    public ConsoleApplication() {
        this.sc = new Scanner(System.in);
        this.commentConsoleMenu = new CommentConsoleMenu(sc, new CommentDaoImpl());
        this.taskConsoleMenu = new TaskConsoleMenu(sc, new TaskDaoImpl());
    }

    //start method
    public void start() {
        while (true) {
            int choice = menu();
            switch (choice) {
                case 1:
                    taskConsoleMenu.addTask();
                    break;
                case 6:
                    commentConsoleMenu.addComment();
                    break;
                case 7:
                    //commentConsoleMenu.updateComment();
                    break;
                case 9:
                    break;
                case 11:
                    System.out.println("Bye, have a nice day!");
                    System.exit(0);
                default:
                    break;
            }
        }
    }

    //menu
    private int menu() {
        System.out.println("--------WELCOME--------");
        System.out.println("-------MENU TASK-------");
        System.out.println("  1. Create task");
        System.out.println("  2. Update task");
        System.out.println("  3. Pull task by id");
        System.out.println("  4. Pull all tasks");
        System.out.println("  5. Delete task");
        System.out.println("------MENU COMMENT-----");
        System.out.println("  6. Create comment");
        System.out.println("  7. Update comment");
        System.out.println("  8. Pull comment by id");
        System.out.println("  9. Pull all comment");
        System.out.println("  10. Delete comment");
        System.out.println("  11. Exit");
        int choice = chooseMenuItem(1, 11);
        return choice;
    }

    // check is the input number is right
    private int chooseMenuItem(int min, int max) {
        int choice = 0;
        try {
            choice = Integer.parseInt(sc.nextLine());
            if (choice > max || choice < min) {
                log.info("The entered number {} is not a menu item", choice);
            }
        } catch (NumberFormatException e) {
            log.error("You need to input a number from 1 to 11");
        }

        return choice;
    }
}


