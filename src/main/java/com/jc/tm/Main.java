package com.jc.tm;

import java.util.Scanner;

import com.jc.tm.database.dao.TaskDao;
import com.jc.tm.database.dao.TaskDaoImpl;

/**
 * this main class for launch a whole application
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        TaskDao tasks = new TaskDaoImpl();

        System.out.println("What you want to do?"
                + "\n1. Create task."
                + "\n2. Update task"
                + "\n3. View all tasks."
                + "\n4. Find task."
                + "\n5. Delete task.");

        int operation = in.nextInt();

        switch (operation) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                tasks.selectAllTask();
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                System.out.println("Wrong choice!");
                break;
        }
    }
}
