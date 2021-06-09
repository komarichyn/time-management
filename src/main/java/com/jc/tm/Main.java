package com.jc.tm;

import com.jc.tm.database.dao.CommentDaoImpl;
import com.jc.tm.database.dao.TaskDaoImpl;
import com.jc.tm.helper.DatabaseHelper;
import com.jc.tm.service.PaginationDto;
import com.jc.tm.service.TaskServiceImpl;
import com.jc.tm.ui.TaskConsole;
import com.jc.tm.ui.console.MyConsole;

import java.sql.SQLException;

/**
 * this main class for launch a whole application
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
//        ConsoleApplication consoleApplication = new ConsoleApplication();
//        consoleApplication.start();

        TaskDaoImpl taskDao = new TaskDaoImpl(DatabaseHelper.getInstance());
        CommentDaoImpl commentDao = new CommentDaoImpl(DatabaseHelper.getInstance());
//        TaskServiceImpl taskService = new TaskServiceImpl(taskDao,commentDao);
//
//        TaskConsole console = new TaskConsole(taskService);
//        console.start();

        TaskServiceImpl taskService = new TaskServiceImpl(taskDao,commentDao);
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setIndex(0);
        paginationDto.setSize(5);
        try {
            taskService.loadTasks(paginationDto);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
