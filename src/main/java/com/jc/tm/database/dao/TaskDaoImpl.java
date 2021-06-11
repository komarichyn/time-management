package com.jc.tm.database.dao;

import com.jc.tm.database.Status;
import com.jc.tm.database.entity.Task;

import com.jc.tm.helper.DatabaseHelper;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jc.tm.service.PaginationDto;
import lombok.extern.slf4j.Slf4j;

import static java.sql.Timestamp.valueOf;

/**
 * this TaskDaoImpl class realize all CRUD methods for task
 */
@Slf4j
public class TaskDaoImpl implements TaskDao {

  //sql commands
  private static final String INSERT_TASK = "INSERT INTO task (id, name, description, created, status) VALUES (NULL, ?, ?, ?, ?)";
  private static final String UPDATE_TASK = "UPDATE task SET name = ?, description = ?, created = ?, status = ? WHERE id = ?";
  private static final String SELECT_ALL_TASK = "SELECT id, name, description, created, status FROM task";
  private static final String SELECT_BY_ID_TASK = "SELECT id, name, description, created, status FROM task WHERE id = ?";
  private static final String DELETE_TASK = "DELETE FROM task WHERE id = ?";
  private static final String GET_FIVE_DUE_DATE_TASKS = "SELECT * FROM task WHERE due_date > now() order by due_date limit ?,?";

  //name of sql fields
  private static final String _ID = "id";
  private static final String _NAME = "name";
  private static final String _DESCRIPTION = "description";
  private static final String _CREATED = "created";
  private static final String _STATUS = "status";
  private static final String _DUE_DATE = "due_date";

  private DatabaseHelper dbHelper;

  public TaskDaoImpl(DatabaseHelper dbHelper) {
    this.dbHelper = dbHelper;
  }

  @Override
  public Task insert(Task task) throws SQLException {
    log.debug("insert new task:{}", task);
    var connection = dbHelper.getConnection();

    try (var preparedStatement = connection.prepareStatement(INSERT_TASK, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, task.getName());
      preparedStatement.setString(2, task.getDescription());
      preparedStatement.setTimestamp(3, valueOf(LocalDateTime.now()));
      preparedStatement.setString(4, task.getStatus().toString());

      preparedStatement.executeUpdate();
      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        task.setId(generatedKeys.getLong(1));
      }
      log.debug("new task {} was saved", task);

    } finally {
      dbHelper.closeConnection(connection);
    }
    return task;
  }

  @Override
  public void update(Task task) throws SQLException {
    log.debug("update task:{}", task);
    var connection = dbHelper.getConnection();

    try (var preparedStatement = connection.prepareStatement(UPDATE_TASK)) {
      preparedStatement.setString(1, task.getName());
      preparedStatement.setString(2, task.getDescription());
      preparedStatement.setTimestamp(3, valueOf(LocalDateTime.now()));
      preparedStatement.setString(4, task.getStatus().toString());
      preparedStatement.setLong(5, task.getId());

      preparedStatement.executeUpdate();
      log.debug("task was updated");

    } finally {
      dbHelper.closeConnection(connection);
    }
  }

  @Override
  public Task getById(Long id) throws SQLException {
    var connection = dbHelper.getConnection();

    try (var preparedStatement = connection.prepareStatement(SELECT_BY_ID_TASK)) {
      preparedStatement.setLong(1, id);
      var resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        return this.buildTask(resultSet);
      }

    } finally {
      dbHelper.closeConnection(connection);
    }

    return null;
  }

  @Override
  public List<Task> getAll() throws SQLException {
    List<Task> taskList = new ArrayList<>();
    var connection = dbHelper.getConnection();

    try (var preparedStatement = connection.prepareStatement(SELECT_ALL_TASK)) {
      var resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        taskList.add(buildTask(resultSet));
      }
    } finally {
      dbHelper.closeConnection(connection);
    }

    return taskList;
  }

  @Override
  public boolean delete(Task task) throws SQLException {
    log.debug("delete task:{}", task);
    var connection = dbHelper.getConnection();

    try (var preparedStatement = connection.prepareStatement(DELETE_TASK)) {
      preparedStatement.setLong(1, task.getId());
      boolean result = preparedStatement.executeUpdate() > 0;
      if (log.isDebugEnabled()) {
        if (result) {
          log.debug("task {} was delete", task);
        } else {
          log.debug("task {} not found", task);
        }
      }
      return result;
    } finally {
      dbHelper.closeConnection(connection);
    }
  }

  @Override
  public List<Task> getFiveDueDateTasks(PaginationDto paginationDto) throws SQLException {
    List<Task> dueDateTaskList = new ArrayList<>();
    var connection = dbHelper.getConnection();

    try (var preparedStatement = connection.prepareStatement(GET_FIVE_DUE_DATE_TASKS)) {
      preparedStatement.setInt(1,paginationDto.getIndex());
      preparedStatement.setInt(2,paginationDto.getSize());
      var resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        dueDateTaskList.add(buildTask(resultSet));
      }
    } finally {
      dbHelper.closeConnection(connection);
    }
    return dueDateTaskList;
  }

  private Task buildTask(ResultSet resultSet) throws SQLException {
    var task = new Task();
    task.setId(resultSet.getLong(_ID));
    task.setName(resultSet.getString(_NAME));
    task.setDescription(resultSet.getString(_DESCRIPTION));
    task.setCreated(LocalDateTime.from(resultSet.getTimestamp(_CREATED).toLocalDateTime()));
    task.setStatus(Status.valueOf(resultSet.getString(_STATUS)));
    task.setDueDate(LocalDateTime.from(resultSet.getTimestamp(_DUE_DATE).toLocalDateTime()));
    return task;
  }
}
