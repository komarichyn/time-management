package com.jc.tm;

import com.jc.tm.database.dao.CommentDao;
import com.jc.tm.database.dao.CommentDaoImpl;
import com.jc.tm.database.dao.TaskDao;
import com.jc.tm.database.dao.TaskDaoImpl;
import com.jc.tm.helper.DatabaseHelper;
import com.jc.tm.service.ITaskService;
import com.jc.tm.service.PaginationDto;
import com.jc.tm.service.TaskServiceImpl;
import com.jc.tm.ui.TaskConsole;
import com.jc.tm.ui.console.MyConsole;

import java.sql.SQLException;
import javax.swing.plaf.basic.BasicListUI.ListDataHandler;

/**
 * this main class for launch a whole application
 */
public class Main {

    public static void main(String[] args) {
        var dataHelper = DatabaseHelper.getInstance();
        TaskDao taskDao = new TaskDaoImpl(dataHelper);
        CommentDao commentDao = new CommentDaoImpl(dataHelper);
        ITaskService taskService = new TaskServiceImpl(taskDao,commentDao);
        TaskConsole console = new TaskConsole(taskService);
        console.launch();

    }
}
