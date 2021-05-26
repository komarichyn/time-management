package com.jc.tm.userInterface;

import com.jc.tm.database.Status;
import com.jc.tm.database.dao.TaskDao;
import com.jc.tm.database.entity.Task;
import lombok.extern.slf4j.Slf4j;
import java.sql.SQLException;
import java.util.Scanner;

@Slf4j
public class TaskConsoleMenu {
    private TaskDao taskDao;
    private Scanner sc;

    public TaskConsoleMenu(Scanner sc, TaskDao taskDao) {
        this.taskDao = taskDao;
        this.sc = sc;
    }

    public void addTask() {
        var task = new Task();
        System.out.println("Write task name: ");
        task.setName(sc.nextLine());
        System.out.println("Write task description: ");
        task.setDescription(sc.nextLine());
        System.out.println("Set task status");
        task.setStatus(Status.valueOf(sc.nextLine()));
        try {
            taskDao.insert(task);
        } catch (SQLException e) {
            log.error("wrong input", e);

        }
    }
}
