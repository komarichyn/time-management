package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {
    private Connection connection = Jdbc.getConnection();

    @Override
    public Long insert(Comment comment){
        PreparedStatement preparedStatement = null;
        Long id = null;
        String sql = "INSERT INTO COMMENT(TASK_ID, TEXT, CREATED) VALUES (?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, comment.getTask_id());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                id = generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            //gonna log this later
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e){
                //gonna log this later
                System.out.println(e.getMessage());
            }
        }
        return id;
    }

    @Override
    public void update(Comment comment) {

    }

    @Override
    public Comment getById(int id) {
        return null;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> commentList = new ArrayList<>();
        String sql = "SELECT ID, TASK_ID, TEXT, CREATED FROM COMMENT";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getLong("ID"));
                comment.setTask_id(resultSet.getLong("TASK_ID"));
                comment.setText(resultSet.getString("TEXT"));
                comment.setCreated(resultSet.getTimestamp("CREATED").toLocalDateTime());
                commentList.add(comment);
            }
            //gonna log this later
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try{
                if (statement != null) { statement.close(); }
                if (connection != null) { connection.close(); }
            }catch (SQLException e){
                //gonna log this later
                System.out.println(e.getMessage());
            }
        }
        return commentList;
    }

    @Override
    public boolean delete(Comment comment) {
        return false;
    }
}