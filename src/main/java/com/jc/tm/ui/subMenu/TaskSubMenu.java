package com.jc.tm.ui.subMenu;

import com.jc.tm.database.Status;
import com.jc.tm.database.entity.Task;
import com.jc.tm.service.ITaskService;
import com.jc.tm.ui.console.MyDevice;

import java.sql.SQLException;

public class TaskSubMenu {
    private MyDevice console;
    private ITaskService service;

    public TaskSubMenu(MyDevice console, ITaskService service) {
        this.console = console;
        this.service = service;
    }

    public void createTask() {
        //TODO create log system
        var task = new Task();
        console.printf("Please enter your task:%n");
        console.printf("Enter name: ");
        String nameTask = console.readLine();
        task.setName(nameTask);
        console.printf("Enter description: ");
        String descriptionTask = console.readLine();
        task.setDescription(descriptionTask);
        console.printf("Enter status: ");
        Status statusTask = Status.valueOf(console.readLine());
        task.setStatus(statusTask);
        try {
            service.saveTask(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask() {
        //TODO create log system
        var task = new Task();
        console.printf("Enter task id to change: ");
        Long taskId = Long.parseLong(console.readLine());
        task.setId(taskId);
        console.printf("Enter task name to change: ");
        String nameTask = console.readLine();
        task.setName(nameTask);
        console.printf("Enter task description to change: ");
        String descriptionTask = console.readLine();
        task.setDescription(descriptionTask);
        console.printf("Enter task status to change: ");
        Status statusTask = Status.valueOf(console.readLine());
        task.setStatus(statusTask);
        try {
            service.updateTask(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getByIdTask() {
        //TODO create log system
        console.printf("Enter task id to view: ");
        Long taskId = Long.parseLong(console.readLine());
        try {
            console.printf(String.valueOf(service.getTask(taskId)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllTask() {
//        TODO create pagination
    }

    public void removeTask() {
        //TODO create log system
        console.printf("Enter task id to delete: ");
        Long taskId = Long.parseLong(console.readLine());
        try {
            service.removeTask(taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
