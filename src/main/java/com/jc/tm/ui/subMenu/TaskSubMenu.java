package com.jc.tm.ui.subMenu;

import com.jc.tm.db.Status;
import com.jc.tm.db.entity.Task;
import com.jc.tm.service.ITaskService;
import com.jc.tm.service.PaginationDto;
import com.jc.tm.ui.console.MyDevice;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TaskSubMenu {
    private MyDevice console;
    private ITaskService service;
    Task task;
    PaginationDto paginationDto;

    public TaskSubMenu(MyDevice console, ITaskService service) {
        this.console = console;
        this.service = service;
    }

    public void createTask() {
        log.debug("createTask: in TaskSubMenu");
        task = new Task();
        console.printf("Please enter your task:%n");
        this.enterData();
        log.debug("createTask: task from user {}", task);
        service.saveTask(task);
    }

    public void updateTask() {
        log.debug("updateTask: in TaskSubMenu");
        task = new Task();
        console.printf("Enter task id to change: ");
        Long taskId = Long.parseLong(console.readLine());
        task.setId(taskId);
        this.enterData();
        log.debug("updateTask: task from user {}", task);
        service.updateTask(task);

    }

    public void getByIdTask() {
        log.debug("getByIdTask: in TaskSubMenu");
        console.printf("Enter task id to view: ");
        Long taskId = Long.parseLong(console.readLine());
        console.clear();
        log.debug("getByIdTask: id from user {}", taskId);
        if (service.getTask(taskId) != null) {
            console.printf(String.valueOf(service.getTask(taskId)));
        } else {
            console.printf("Element not found");
        }

    }

    public int getFiveDueDateTasks(int page) {
        log.debug("getFiveDueDateTasks: in TaskSubMenu");
//        paginationDto = new PaginationDto(page, 5, 1);
        if (service.loadTasks(paginationDto).isEmpty()) {
            return 0;
        }

        return paginationDto.getSize();
    }

    public void removeTask() {
        log.debug("removeTask: in TaskSubMenu");
        console.printf("Enter task id to delete: ");
        Long taskId = Long.parseLong(console.readLine());
        log.debug("removeTask: id from user {}", taskId);
        if (service.removeTask(taskId) != null) {
            console.printf("Element not found");
        } else {
            console.printf("Element not deleted");
        }

    }

    private void enterData() {
        log.debug("enterData: data from user");
        console.printf("Enter name: ");
        String nameTask = console.readLine();
        task.setName(nameTask);
        console.printf("Enter description: ");
        String descriptionTask = console.readLine();
        task.setDescription(descriptionTask);
        console.printf("Enter status: ");
        Status statusTask = Status.valueOf(console.readLine());
        task.setStatus(statusTask);
        log.debug("Name {}, description {}, status {}", nameTask, descriptionTask, statusTask);
    }
}
