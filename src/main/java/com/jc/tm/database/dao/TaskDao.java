package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

import java.util.List;

public interface TaskDao {

    //create or insert task
    void insetTask(Task task);

    //update task
    void updateTask(Task task);

    //select task
    Task selectTask(int id);

    //select all task
    List<Task> selectAllTask();

    //delete task
    void deleteTask(Task task);
}
