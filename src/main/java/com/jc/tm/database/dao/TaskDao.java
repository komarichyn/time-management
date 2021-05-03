package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

public interface TaskDao {

    //create or insert task
    void insetTask(Task task);

    //update task
    void updateTask(Task task);

    //select task
    Task getTaskById(int id);

    //select all task
    void allTask();

    //delete task
    void deleteTask(Task task);
}
