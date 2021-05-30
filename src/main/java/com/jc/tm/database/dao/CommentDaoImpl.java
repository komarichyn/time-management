package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Comment;
import com.jc.tm.helper.DatabaseHelper;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import static java.sql.Timestamp.valueOf;

/**
 * this CommentDaoImpl class realize all CRUD methods for task
 */
@Slf4j
public class CommentDaoImpl implements CommentDao {

    //name of sql fields
    private static final String _ID = "ID";
    private static final String _TASK_ID = "TASK_ID";
    private static final String _TEXT = "TEXT";
    private static final String _CREATED = "CREATED";
    //sql commands
    private static final String INSERT = "INSERT INTO COMMENT(ID, TASK_ID, TEXT, CREATED) VALUES (NULL, ?, ?, ?)";
    private static final String UPDATE = "UPDATE COMMENT SET TEXT = ?, CREATED = ? WHERE ID = ?";
    private static final String GET_BY_ID = "SELECT ID, TASK_ID, TEXT, CREATED FROM COMMENT WHERE ID = ? ";
    private static final String GET_ALL = "SELECT ID, TASK_ID, TEXT, CREATED FROM COMMENT";
    private static final String DELETE = "DELETE FROM COMMENT WHERE ID = ? ";

    private DatabaseHelper dbHelper;

    public CommentDaoImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Comment insert(Comment comment) throws SQLException{
        log.debug("insert new comment:{}", comment);
        var connection = dbHelper.getConnection();

        try (var preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, comment.getTaskId());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setTimestamp(3, valueOf(LocalDateTime.now()));

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                comment.setId(generatedKeys.getLong(1));
            }
            log.debug("new comment {} was added", comment);

        } finally {
            dbHelper.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public void update(Comment comment) throws SQLException {
        log.debug("update comment:{}", comment);
        var connection = dbHelper.getConnection();

        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, comment.getText());
            preparedStatement.setTimestamp(2, valueOf(LocalDateTime.now()));
            preparedStatement.setLong(3, comment.getId());

            preparedStatement.executeUpdate();
            log.debug("comment was updated");

        } finally {
            dbHelper.closeConnection(connection);
        }
    }

    @Override
    public Comment getById(Long id) throws SQLException {
        var connection = dbHelper.getConnection();

        try (var preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                var comment = new Comment();
                comment.setId(resultSet.getLong(_ID));
                comment.setTaskId(resultSet.getLong(_TASK_ID));
                comment.setText(resultSet.getString(_TEXT));
                comment.setCreated(resultSet.getTimestamp(_CREATED).toLocalDateTime());
                return comment;
            }

        } finally {
            dbHelper.closeConnection(connection);
        }

        return null;
    }

    @Override
    public List<Comment> getAll() throws SQLException {
        var connection = dbHelper.getConnection();
        List<Comment> commentList = new ArrayList<>();
        try (var preparedStatement = connection.prepareStatement(GET_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var comment = new Comment();
                comment.setId(resultSet.getLong(_ID));
                comment.setTaskId(resultSet.getLong(_TASK_ID));
                comment.setText(resultSet.getString(_TEXT));
                comment.setCreated(resultSet.getTimestamp(_CREATED).toLocalDateTime());
                commentList.add(comment);
            }
        } finally {
            dbHelper.closeConnection(connection);
        }

        return commentList;
    }

    @Override
    public boolean delete(Comment comment) throws SQLException {
        log.debug("delete comment:{}", comment);
        var connection = dbHelper.getConnection();

        try (var preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, comment.getId());
            boolean result = preparedStatement.executeUpdate() > 0;
            if (log.isDebugEnabled()) {
                if (result) {
                    log.debug("comment {} was delete", comment);
                } else {
                    log.debug("comment {} not found", comment);
                }
            }
            return result;
        } finally {
            dbHelper.closeConnection(connection);
        }
    }
}