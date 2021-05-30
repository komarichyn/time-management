package com.jc.tm;

import com.jc.tm.database.dao.CommentDaoImpl;
import com.jc.tm.database.dao.TaskDaoImpl;
import com.jc.tm.helper.DatabaseHelper;
import com.jc.tm.service.TaskServiceImpl;
import com.jc.tm.ui.TaskConsole;
import com.jc.tm.ui.console.MyConsole;

/**
 * this main class for launch a whole application
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
//        ConsoleApplication consoleApplication = new ConsoleApplication();
//        consoleApplication.start();
        TaskDaoImpl taskDao = new TaskDaoImpl(DatabaseHelper.getInstance());
        CommentDaoImpl commentDao = new CommentDaoImpl(DatabaseHelper.getInstance());
        TaskServiceImpl taskService = new TaskServiceImpl(taskDao,commentDao);

        TaskConsole console = new TaskConsole(taskService);
        console.start();


    }
}
