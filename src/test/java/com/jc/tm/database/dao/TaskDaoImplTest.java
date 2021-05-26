package com.jc.tm.database.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.jc.tm.database.Status;
import com.jc.tm.database.entity.Task;
import com.jc.tm.helper.DatabaseHelper;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskDaoImplTest {

  TaskDaoImpl dao;

  @BeforeEach
  void setUp() {
    dao = new TaskDaoImpl(DatabaseHelper.getInstance());
  }

  @AfterEach
  void tearDown() {
    dao = null;//not necessary
  }

  private Task generateTask(){
    Task task = new Task();
    task.setCreated(LocalDate.now());
    task.setDescription("some description");
    task.setName("Task name");
    task.setStatus(Status.TODO);
    return task;
  }

  @Test
  void insert() {
    Task result = null;
    try {
      result = dao.insert(generateTask());
    } catch (SQLException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    assertNotNull(result);
    assertNotNull(result.getId());
  }

  @Test
  void update() {
  }

  @Test
  void getById() {
  }

  @Test
  void getAll() {
  }

  @Test
  void delete() {
  }
}