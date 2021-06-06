package com.jc.tm.ui.subMenu;

import com.jc.tm.database.Status;
import com.jc.tm.database.dao.TaskDaoImpl;
import com.jc.tm.database.entity.Task;
import com.jc.tm.helper.DatabaseHelper;
import com.jc.tm.service.ITaskService;
import com.jc.tm.ui.console.MyDevice;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskSubMenu {
    private MyDevice console;
    private ITaskService service;
    Task task;
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(); // TODO DELETE!

    public TaskSubMenu(MyDevice console, ITaskService service) {
        this.console = console;
        this.service = service;
    }

    public void createTask() {
        log.debug("createTask: in TaskSubMenu");
        task = new Task();
        console.printf("Please enter your task:%n");
        this.enterData();
        try {
            log.debug("createTask: task from user {}", task);
            service.saveTask(task);
        } catch (SQLException e) {
            log.error("createTask: problem with {}", task);
            e.printStackTrace();
        }
    }

    public void updateTask() {
        log.debug("updateTask: in TaskSubMenu");
        task = new Task();
        console.printf("Enter task id to change: ");
        Long taskId = Long.parseLong(console.readLine());
        task.setId(taskId);
        this.enterData();
        try {
            log.debug("updateTask: task from user {}", task);
            service.updateTask(task);
        } catch (SQLException e) {
            log.error("updateTask: problem with {}", task);
            e.printStackTrace();
        }
    }

    public void getByIdTask() {
        log.debug("getByIdTask: in TaskSubMenu");
        console.printf("Enter task id to view: ");
        Long taskId = Long.parseLong(console.readLine());
        try {
            log.debug("getByIdTask: id from user {}", taskId);
            console.printf(String.valueOf(service.getTask(taskId)));
        } catch (SQLException e) {
            log.error("getByIdTask: problem with {}", taskId);
            e.printStackTrace();
        }
    }

    public int getAllTask(int page) {
        //    TODO create pagination
        //    TODO DELETE!
        String select = "SELECT * FROM task ORDER BY created DESC LIMIT " + page + ", 5";
        List<Task> taskList = new ArrayList<>();
        var connection = dbHelper.getConnection();
        try (var preparedStatement = connection.prepareStatement(select)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                taskList.add(buildTask(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeConnection(connection);
        }
        for(int i = 0; i < taskList.size(); i++) {
            System.out.println(taskList.get(i) + " Page " + page);
        }
        taskList.clear();
        return page;
        //    TODO DELETE!
    }

    //    TODO DELETE!
    private static final String _ID = "id";
    private static final String _NAME = "name";
    private static final String _DESCRIPTION = "description";
    private static final String _CREATED = "created";
    private static final String _STATUS = "status";

    private Task buildTask(ResultSet resultSet) throws SQLException {
        var task = new Task();
        task.setId(resultSet.getLong(_ID));
        task.setName(resultSet.getString(_NAME));
        task.setDescription(resultSet.getString(_DESCRIPTION));
        task.setCreated(LocalDateTime.from(resultSet.getTimestamp(_CREATED).toLocalDateTime()));
        task.setStatus(Status.valueOf(resultSet.getString(_STATUS)));
        return task;
    }

    //    TODO DELETE!
    public void removeTask() {
        log.debug("removeTask: in TaskSubMenu");
        console.printf("Enter task id to delete: ");
        Long taskId = Long.parseLong(console.readLine());
        try {
            log.debug("removeTask: id from user {}", taskId);
            service.removeTask(taskId);
        } catch (SQLException e) {
            log.error("removeTask: problem with {}", taskId);
            e.printStackTrace();
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
