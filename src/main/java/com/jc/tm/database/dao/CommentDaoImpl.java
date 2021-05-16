package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {
    private static final String ID = "ID";
    private static final String TASK_ID = "TASK_ID";
    private static final String TEXT = "TEXT";
    private static final String CREATED = "CREATED";
    private static final String INSERT = "INSERT INTO COMMENT(TASK_ID, TEXT, CREATED) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE COMMENT SET TEXT = ?, CREATED = ? WHERE ID = ?";
    private static final String GET_BY_ID = "SELECT ID, TASK_ID, TEXT, CREATED FROM COMMENT WHERE ID = ? ";
    private static final String GET_ALL = "SELECT ID, TASK_ID, TEXT, CREATED FROM COMMENT";
    private static final String DELETE = "DELETE FROM COMMENT WHERE ID = ? ";

    Connection connection = Jdbc.getConnection();

    @Override
    public void insert(Comment comment) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setLong(1, comment.getTaskId());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //gonna log this later
            System.out.println(e.getMessage());
            connection.rollback();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void update(Comment comment) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, comment.getText());
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            preparedStatement.setLong(3, comment.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //gonna log this later
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public Comment getById(Long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Comment comment = new Comment();
        try {
            preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                comment.setId(resultSet.getLong(ID));
                comment.setTaskId(resultSet.getLong(TASK_ID));
                comment.setText(resultSet.getString(TEXT));
                comment.setCreated(resultSet.getTimestamp(CREATED).toLocalDateTime());
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() throws SQLException {
        List<Comment> commentList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getLong(ID));
                comment.setTaskId(resultSet.getLong(TASK_ID));
                comment.setText(resultSet.getString(TEXT));
                comment.setCreated(resultSet.getTimestamp(CREATED).toLocalDateTime());
                commentList.add(comment);
            }
            //gonna log this later
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return commentList;
    }

    @Override
    public boolean delete(Comment comment) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean commentDeleted = true;
        try {
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, comment.getId());
            commentDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return commentDeleted;
    }
}