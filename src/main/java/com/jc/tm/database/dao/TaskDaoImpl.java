package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl implements TaskDao{

    List<Task> tasks = new ArrayList<>();

    @Override
    public void insetTask(Task task) {

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public Task selectTask(int id) {
        return null;
    }

    @Override
    public List<Task> selectAllTask() {
        return tasks;
    }

    @Override
    public void deleteTask(Task task) {

    }
}
