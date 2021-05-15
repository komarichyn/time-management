package com.jc.tm;

import com.jc.tm.database.Status;
import com.jc.tm.database.dao.Jdbc;
import com.jc.tm.database.dao.TaskDaoImpl;
import com.jc.tm.database.entity.Task;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * this main class for launch a whole application
 */
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Jdbc.getConnection();
        TaskDaoImpl taskDaoImpl = new TaskDaoImpl();
        Task task = new Task();

        /*task.setId(3);
        task.setName("TaskDaoImpl");
        task.setDescription("In this task I must realize TaskDaoImpl class with all methods.");
        task.setCreated(LocalDate.now());*/
//        task.setStatus(Status.IN_PROGRESS);
//        task.setComments();
//        taskDaoImpl.insert(task);

//        task.setId(3);
//        taskDaoImpl.delete(task);

        /*task.setName("taskdaoimpl");
        task.setDescription("New description");
        task.setCreated(LocalDate.now());
        task.setId(3);
        taskDaoImpl.update(task);*/

//        taskDaoImpl.getById(3);
        System.out.println(taskDaoImpl.getById(3));
    }
}
