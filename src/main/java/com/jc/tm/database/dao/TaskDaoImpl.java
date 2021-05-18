package com.jc.tm.database.dao;

import com.jc.tm.database.Status;
import com.jc.tm.database.entity.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * this TaskDaoImpl class realize all CRUD methods for task
 */
public class TaskDaoImpl implements TaskDao{

    //field for log
    public static final Logger LOGGER = Logger.getLogger(TaskDaoImpl.class.getName());

    //sql commands
    private static final String INSERT_TASK = "INSERT INTO task (id, name, description, due_Dates, status) " +
            "values (?, ?, ?, ?, ?)";
    private static final String UPDATE_TASK = "UPDATE task SET name = ?, description = ?, due_Dates = ? where id = ?";
    private static final String SELECT_ALL_TASK = "SELECT id, name, description, due_Dates, status FROM TASK";
    private static final String SELECT_BY_ID_TASK = "select id, name, description, due_Dates, status from task where id = ?";
    private static final String DELETE_TASK = "DELETE FROM task WHERE id = ?";

    //name of sql fields
    private static final String _ID = "id";
    private static final String _NAME = "name";
    private static final String _DESCRIPTION = "description";
    private static final String _DUEDATES = "due_Dates";
    private static final String _STATUS = "status";

    Connection connection = Jdbc.getConnection();

    @Override
    public void insert(Task task) throws SQLException {
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(INSERT_TASK);
        preparedStatement.setLong(1, task.getId());
        preparedStatement.setString(2, task.getName());
        preparedStatement.setString(3, task.getDescription());
        preparedStatement.setDate(4, Date.valueOf(task.getCreated()));
        preparedStatement.setString(5, task.getStatus().toString());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Task task) throws SQLException {
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(UPDATE_TASK);
        preparedStatement.setString(1, task.getName());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setDate(3, Date.valueOf(task.getCreated()));
        preparedStatement.setLong(4, task.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public Task getById(Long id) throws SQLException {
        Task task = new Task();

        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement(SELECT_BY_ID_TASK);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            //Display values
            task.setId(resultSet.getLong(_ID));
            task.setName(resultSet.getString(_NAME));
            task.setDescription(resultSet.getString(_DESCRIPTION));
            task.setCreated(resultSet.getDate(_DUEDATES).toLocalDate());
            task.setStatus(Status.valueOf(resultSet.getString(_STATUS)));
        }
        preparedStatement.execute();
        return task;
    }

    @Override
    public List<Task> getAll() throws SQLException {
        List<Task> taskList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement(SELECT_ALL_TASK);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            //Display values
            Task task = new Task();
            task.setId(resultSet.getLong(_ID));
            task.setName(resultSet.getString(_NAME));
            task.setDescription(resultSet.getString(_DESCRIPTION));
            task.setCreated(resultSet.getDate(_DUEDATES).toLocalDate());
            task.setStatus(Status.valueOf(resultSet.getString(_STATUS)));

            taskList.add(task);
        }
        return taskList;
    }

    @Override
    public boolean delete(Task task) throws SQLException {
        boolean rowDeleted;
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(DELETE_TASK);
        preparedStatement.setLong(1, task.getId());
        rowDeleted = preparedStatement.executeUpdate() > 0;

        return rowDeleted;
    }
}
