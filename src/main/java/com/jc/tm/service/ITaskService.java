package com.jc.tm.service;

import com.jc.tm.database.Status;
import com.jc.tm.database.entity.Comment;
import com.jc.tm.database.entity.Task;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface ITaskService {
    /**
     * save task entity into database.
     * @param newTask new entity wich will present task object
     * @return saved entity with generated id;
     */
    public Task saveTask(Task newTask) throws SQLException;

    /**
     * remove task from database. the task will be removed by id, but first method should find proper record into database
     * @param id identiifier for task
     * @return removed task object or null if task by id does not exists
     */
    public Task removeTask(Long id) throws SQLException;

    /**
     * remove task from data base. this method calls @see removeTask(Long id)
     * @param task object task with identifier for monipulation
     * @return removed task object of null if task does not exists
     */
    public Task removeTask(Task task) throws SQLException;

    /**
     * update task. method do update or replace existing object into database. only identifier of object must be constant
     * @param freshTask new object or updated object of task
     * @return updated object of task
     */
    public Task updateTask(Task freshTask) throws SQLException;

    /**
     * find task object by id
     * @param id idetnifier of task
     * @return Task object or null if object was not found
     */
    public Task getTask(Long id) throws SQLException;

    /**
     * find object task. this method calls getTask(Long id)
     * @param task template for finding
     * @return task object
     */
    public Task getTask(Task task) throws SQLException;

    /**
     * default call for loading task list. but result will be split on 20 items
     * @return list of tasks
     */
    public Collection<Task> loadTasks() throws SQLException;

    /**
     * load task list by custom criteria. data should be loaded by pagination which passes via parameters
     * @param page pagiantion configuraion
     * @return list of tasks
     */
    public Collection<Task> loadTasks(PaginationDto page) throws SQLException;
    public Collection<Task> loadTasksByDescPriority(PaginationDto page);
    public Collection<Task> loadTasksByAskPriority(PaginationDto page);
    public Task addComment(Long taskId, Comment newComment) throws SQLException;
    public Task addCommnet(Task task, Comment newComment) throws SQLException;
    public Comment removeComment(Long id) throws SQLException;
    public Comment removeComment(Comment comment) throws SQLException;
    public Comment updateComment(Comment freshComment) throws SQLException;
    public Task setDueDate(Task task, LocalDateTime time) throws SQLException;
    public Task setDueDate(Long taskId, LocalDateTime time) throws SQLException;
    public Task updateDueDate(Task task, LocalDateTime time);
    public Task updateDueDate(Long taskId, LocalDateTime time);
    public Task setPriority(Task task, Priority priority);
    public Task setParentTask(Task parent, Task current);

    /**
     * remove link to parent task
     * @param task current task whiich will be moved to root of task list
     * @return updated task
     */
    public Task moveTaskToRoot(Task task);

    /**
     * move task to pause state
     * @param task
     * @return
     */
    public Task toPauseState(Task task);

    /**
     * change state of task, by default state of task should be Status.TODO
     * @param task task
     * @param newState new state
     * @return updated task
     */
    public Task setState(Task task, Status newState);


}
