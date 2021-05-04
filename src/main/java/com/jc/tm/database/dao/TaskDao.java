package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

import java.util.List;

public interface TaskDao extends BaseDao<Task> {
    @Override
    default void insert(Task task) {

    }

    @Override
    default void update(Task task) {

    }

    @Override
    default Task getById(int id) {
        return null;
    }

    @Override
    default List<Task> getAll() {
        return null;
    }

    @Override
    default void delete(Task task) {

    }
}
