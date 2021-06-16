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
import lombok.val;

import static java.sql.Timestamp.valueOf;

/**
 * this TaskDaoImpl class realize all CRUD methods for task
 */
@Slf4j
public class TaskDaoImpl implements TaskDao {

  //sql commands
  private static final String INSERT_TASK = "INSERT INTO task (id, name, description, created, status, due_date) VALUES (NULL, ?, ?, ?, ?, ?)";
  private static final String UPDATE_TASK = "UPDATE task SET name = ?, description = ?, status = ? WHERE id = ?";
  private static final String SELECT_ALL_TASK = "SELECT id, name, description, created, status FROM task";
  private static final String SELECT_BY_ID_TASK = "SELECT id, name, description, created, status, due_date FROM task WHERE id = ?";
  private static final String DELETE_TASK = "DELETE FROM task WHERE id = ?";
  private static final String GET_FIVE_DUE_DATE_TASKS = "SELECT * FROM task WHERE due_date <= now() order by due_date DESC limit ?,?";
  private static final String CHECK_DATABASE = "select * from task";

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

  private Long getGeneratedId(PreparedStatement ps) throws SQLException {
    ResultSet value = ps.getGeneratedKeys();
    if (value.next()) {
      return value.getLong(1);
    }
    return null;
  }

  @Override
  public Task insert(Task task) throws SQLException {
    log.debug("insert new task:{}", task);
    var connection = dbHelper.getConnection();

    try (var ps = connection.prepareStatement(INSERT_TASK, Statement.RETURN_GENERATED_KEYS)) {

      LocalDateTime currentTime = LocalDateTime.now();
      Timestamp createdTime = Timestamp.valueOf(currentTime);
      Timestamp dueTime = createdTime;
      String status = task.getStatus() != null ? task.getStatus().toString() : Status.TODO.toString();
      if (task.getDueDate() != null) {
        dueTime = Timestamp.valueOf(task.getDueDate());
      }

      ps.setString(1, task.getName());
      ps.setString(2, task.getDescription());
      ps.setTimestamp(3, createdTime);
      ps.setString(4, status);
      ps.setTimestamp(5, dueTime);

      ps.executeUpdate();
      task.setId(this.getGeneratedId(ps));
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

    try (var ps = connection.prepareStatement(UPDATE_TASK)) {

      ps.setString(1, task.getName());
      ps.setString(2, task.getDescription());
      ps.setString(3, task.getStatus().toString());
      ps.setLong(4, task.getId());

      ps.executeUpdate();
      log.debug("task was updated");

    } finally {
      dbHelper.closeConnection(connection);
    }
  }

  @Override
  public Task getById(Long id) throws SQLException {
    var connection = dbHelper.getConnection();

    try (var ps = connection.prepareStatement(SELECT_BY_ID_TASK)) {
      ps.setLong(1, id);
      var rs = ps.executeQuery();
      if (rs.next()) {
        return this.buildTask(rs);
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

    try (var ps = connection.prepareStatement(SELECT_ALL_TASK)) {
      var rs = ps.executeQuery();
      while (rs.next()) {
        taskList.add(buildTask(rs));
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

    try (var ps = connection.prepareStatement(DELETE_TASK)) {
      ps.setLong(1, task.getId());
      boolean result = ps.executeUpdate() > 0;
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
  public List<Task> getFiveDueDateTasks(PaginationDto pagination) throws SQLException {
    List<Task> dueDateTaskList = new ArrayList<>();
    var connection = dbHelper.getConnection();

    try (var ps = connection.prepareStatement(GET_FIVE_DUE_DATE_TASKS)) {
      ps.setInt(1, pagination.getIndex());
      ps.setInt(2, pagination.getSize());
      var rs = ps.executeQuery();
      if (!rs.next()) {
        log.warn("Resultset is empty, so database is empty");
      } else {
        do {
          dueDateTaskList.add(buildTask(rs));
        } while (rs.next());
      }
    } finally {
      dbHelper.closeConnection(connection);
    }
    return dueDateTaskList;
  }

  public boolean checkDatabase() throws SQLException {
    var connection = dbHelper.getConnection();
    boolean emptyDatabase = false;
    try (var ps = connection.prepareStatement(CHECK_DATABASE)) {
      var rs = ps.executeQuery();
      if (!rs.next()) {
        log.info("Database is empty");
        emptyDatabase = true;
      }
    } finally {
      dbHelper.closeConnection(connection);
    }
    return emptyDatabase;
  }

  private Task buildTask(ResultSet rs) throws SQLException {
    Timestamp createdDate = rs.getTimestamp(_CREATED);
    Timestamp dueDate = rs.getTimestamp(_DUE_DATE);
    var task = new Task();
    task.setId(rs.getLong(_ID));
    task.setName(rs.getString(_NAME));
    task.setDescription(rs.getString(_DESCRIPTION));
    task.setStatus(Status.valueOf(rs.getString(_STATUS)));

    if(createdDate != null){
      task.setCreated(createdDate.toLocalDateTime());
    }
    if(dueDate != null){
      task.setDueDate(dueDate.toLocalDateTime());
    }
    return task;
  }
}
