package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao extends BaseDao<Task> {
    @Override
    void insert(Task task) throws SQLException;

    @Override
    void update(Task task) throws SQLException;

    @Override
    Task getById(int id) throws SQLException;

    @Override
    List<Task> getAll();

    @Override
    boolean delete(Task task) throws SQLException;
}
