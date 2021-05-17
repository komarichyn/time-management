package com.jc.tm.database.dao;

import com.jc.tm.database.Status;
import com.jc.tm.database.entity.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

    private static final String _id = "id";
    private static final String _name = "name";
    private static final String _description = "description";
    private static final String _dueDates = "due_Dates";
    private static final String _status = "status";

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
            task.setId(resultSet.getLong(_id));
            task.setName(resultSet.getString(_name));
            task.setDescription(resultSet.getString(_description));
            task.setCreated(resultSet.getDate(_dueDates).toLocalDate());
            task.setStatus(Status.valueOf(resultSet.getString(_status)));

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

        /*Statement statement = null;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_ALL_TASK);*/

        while(resultSet.next()) {
            System.err.println("While loop start");
            //Display values
            Task task = new Task();
            task.setId(resultSet.getLong(_id));
            task.setName(resultSet.getString(_name));
            task.setDescription(resultSet.getString(_description));
            task.setCreated(resultSet.getDate(_dueDates).toLocalDate());
            task.setStatus(Status.valueOf(resultSet.getString(_status)));

            taskList.add(task);
            System.err.println("While loop end");
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
