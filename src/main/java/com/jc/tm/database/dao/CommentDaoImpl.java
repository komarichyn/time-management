package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Comment;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.sql.Timestamp.valueOf;

/**
 * this CommentDaoImpl class realize all CRUD methods for task
 */
public class CommentDaoImpl implements CommentDao {
    //field for log
    public static final Logger LOGGER = Logger.getLogger(CommentDaoImpl.class.getName());
    //name of sql fields
    private static final String _ID = "ID";
    private static final String __TASK_ID = "TASK_ID";
    private static final String _TEXT = "TEXT";
    private static final String _CREATED = "CREATED";
    //sql commands
    private static final String INSERT = "INSERT INTO COMMENT(TASK_ID, TEXT, CREATED) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE COMMENT SET TEXT = ?, CREATED = ? WHERE ID = ?";
    private static final String GET_BY_ID = "SELECT ID, TASK_ID, TEXT, CREATED FROM COMMENT WHERE ID = ? ";
    private static final String GET_ALL = "SELECT ID, TASK_ID, TEXT, CREATED FROM COMMENT";
    private static final String DELETE = "DELETE FROM COMMENT WHERE ID = ? ";

    @Override
    public Comment insert(Comment comment) throws SQLException{
        Connection connection = Jdbc.getConnection();
        PreparedStatement preparedStatement = null;
        Long createdCommentId = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, comment.getTaskId());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setTimestamp(3, valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdCommentId = generatedKeys.getLong(1);
            }
            comment.setId(createdCommentId);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Problems with creating comment : ", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            Jdbc.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public void update(Comment comment) throws SQLException {
        Connection connection = Jdbc.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, comment.getText());
            preparedStatement.setTimestamp(2, valueOf(LocalDateTime.now()));
            preparedStatement.setLong(3, comment.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Problems with updating comment : ", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            Jdbc.closeConnection(connection);
        }
    }

    @Override
    public Comment getById(Long id) throws SQLException {
        Connection connection = Jdbc.getConnection();
        PreparedStatement preparedStatement = null;
        Comment comment = new Comment();
        try {
            preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comment.setId(resultSet.getLong(_ID));
                comment.setTaskId(resultSet.getLong(__TASK_ID));
                comment.setText(resultSet.getString(_TEXT));
                comment.setCreated(resultSet.getTimestamp(_CREATED).toLocalDateTime());
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "did not find any comment : ", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            Jdbc.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() throws SQLException {
        Connection connection = Jdbc.getConnection();
        List<Comment> commentList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getLong(_ID));
                comment.setTaskId(resultSet.getLong(__TASK_ID));
                comment.setText(resultSet.getString(_TEXT));
                comment.setCreated(resultSet.getTimestamp(_CREATED).toLocalDateTime());
                commentList.add(comment);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "did not find any comment : ", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            Jdbc.closeConnection(connection);
        }
        return commentList;
    }

    @Override
    public boolean delete(Comment comment) throws SQLException {
        Connection connection = Jdbc.getConnection();
        PreparedStatement preparedStatement = null;
        boolean commentDeleted = true;
        try {
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, comment.getId());
            commentDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "did not find any comment to delete : ", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            Jdbc.closeConnection(connection);
        }
        return commentDeleted;
    }
}