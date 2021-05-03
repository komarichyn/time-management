package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDaoImpl implements TaskDao{

    Connection connection = null;
    PreparedStatement ptmt = null;
    ResultSet resultSet = null;

    @Override
    public void insetTask(Task task) {

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public Task selectTask(int id) {
        return null;
    }

    @Override
    public void selectAllTask() {
        String queryString = "SELECT * FROM task";
        connection = Jdbc.getConnection();
        try {
            ptmt = connection.prepareStatement(queryString);
            resultSet = ptmt.executeQuery();
            while (resultSet.next()){
                System.out.println("Id " + resultSet.getInt("id")
                + "Name " + resultSet.getString("name")
                + "Description " + resultSet.getString("description")
                + "Due dates " + resultSet.getDate("due_Dates")
                + "Status " + resultSet.getObject("status")
                + "Comments " + resultSet.getString("comment")
                + "Attachment " + resultSet.getString("attachment")
                + "Sub tasks " + resultSet.getString("list_of_subtask"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteTask(Task task) {

    }
}
