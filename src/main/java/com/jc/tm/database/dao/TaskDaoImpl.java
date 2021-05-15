package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

import java.awt.event.TextEvent;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TaskDaoImpl implements TaskDao{

    //field for log
    public static final Logger LOGGER = Logger.getLogger(TaskDaoImpl.class.getName());

    //sql commands
    private static final String INSERT_TASK = "INSERT INTO task (id, name, description, due_Dates, status, comment) " +
            "values (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_TASK = "UPDATE task SET name = ?, description = ?, due_Dates = ? where id = ?";
    private static final String SELECT_ALL_TASK = "SELECT * FROM TASK";
    private static final String SELECT_BY_ID_TASK = "select * from task where id = 3";
    private static final String DELETE_TASK = "DELETE FROM task WHERE id = ?";

    Connection connection = Jdbc.getConnection();

    @Override
    public void insert(Task task) throws SQLException {
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(INSERT_TASK);
        preparedStatement.setLong(1, task.getId());
        preparedStatement.setString(2, task.getName());
        preparedStatement.setString(3, task.getDescription());
        preparedStatement.setDate(4, Date.valueOf(task.getCreated()));
        preparedStatement.setObject(5, task.getStatus());
        preparedStatement.setObject(6, task.getComments());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Task task) throws SQLException {
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(UPDATE_TASK);
        preparedStatement.setString(1, task.getName());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setDate(3, Date.valueOf(task.getCreated()));
        preparedStatement.setInt(4, task.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public Task getById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_BY_ID_TASK);

        while(rs.next()) {
            //Display values
            System.out.print("ID: " + rs.getInt("id"));
            System.out.print(", Name: " + rs.getString("name"));
            System.out.print(", Description: " + rs.getString("description"));
            System.out.println(", Created: " + rs.getDate("due_dates"));
        }
        return null;
    }

    @Override
    public List<Task> getAll() {
        return null;
    }

    @Override
    public boolean delete(Task task) throws SQLException {
        boolean rowDeleted;
        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement(DELETE_TASK);
        preparedStatement.setInt(1, task.getId());
        rowDeleted = preparedStatement.executeUpdate() > 0;

        return rowDeleted;
    }
}
