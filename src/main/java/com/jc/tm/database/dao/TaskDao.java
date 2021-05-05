package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

import java.util.List;

public interface TaskDao extends BaseDao<Task> {
    @Override
    void insert(Task task);

    @Override
    void update(Task task);

    @Override
    Task getById(int id);

    @Override
    List<Task> getAll();

    @Override
    boolean delete(Task task);
}
