package com.jc.tm.service;

import com.jc.tm.database.Status;
import com.jc.tm.database.dao.CommentDao;
import com.jc.tm.database.dao.TaskDao;
import com.jc.tm.database.entity.Comment;
import com.jc.tm.database.entity.Task;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * this TaskServiceImpl class cooperate with DAO of Task and Comments
 */
@Slf4j
public class TaskServiceImpl implements ITaskService {

    private final TaskDao taskDao;
    private final CommentDao commentDao;

    public TaskServiceImpl(TaskDao taskDao, CommentDao commentDao) {
        this.taskDao = taskDao;
        this.commentDao = commentDao;
    }

    @Override
    public Task saveTask(Task newTask) throws SQLException {
        log.debug("saveTask input values:{}", newTask);
        newTask = taskDao.insert(newTask);
        log.info("new task {} was saved", newTask);
        return newTask;
    }

    @Override
    public Task removeTask(Long id) throws SQLException {
        log.debug("removeTask input values:{}", id);
        var task = this.getTask(id);
        if (task == null) {
            log.error("Task with id {} not found", id);
            return null;
        } else {
            log.debug("Task was add:{}", task);
            taskDao.delete(task);
        }
        return task;
    }

    @Override
    public Task removeTask(Task task) throws SQLException {
        log.debug("removeTask input task:{}", task);
        return this.removeTask(task.getId());
    }

    @Override
    public Task updateTask(Task freshTask) throws SQLException {
        log.debug("updateTask input values:{}", freshTask);
        var oldTask = this.getTask(freshTask);
        if (oldTask == null) {
            log.error("This task {} not found", freshTask);
            throw new NullPointerException();
        } else {
            log.debug("This task {} was update", freshTask);
            taskDao.update(freshTask);
        }
        return freshTask;
    }

    @Override
    public Task getTask(Long id) throws SQLException {
        log.debug("getTask input values:{}", id);
        Task taskById;
        if (id == null) {
            log.error("Id {} not found", id);
            throw new NullPointerException();
        } else {
            taskById = taskDao.getById(id);
        }
        return taskById;
    }

    @Override
    public Task getTask(Task task) throws SQLException {
        log.debug("getTask input values:{}", task);
        return this.getTask(task.getId());
    }

    @Override
    public Collection<Task> loadTasks() throws SQLException {
        log.debug("load tasks with default pagination");
        return this.loadTasks(PaginationDto.DEFAULT);
    }

    @Override
    public Collection<Task> loadTasks(PaginationDto page) throws SQLException {
        log.debug("load task by pagination: {}", page);
        List<Task> tasks;
        tasks = taskDao.getFiveDueDateTasks(page);
        log.debug("result of call: {}", tasks);
        return tasks;
    }

    @Override
    public Collection<Task> loadTasksByDescPriority(PaginationDto page) {
        return null;
    }

    @Override
    public Collection<Task> loadTasksByAskPriority(PaginationDto page) {
        return null;
    }

    @Override
    public Task addComment(Long taskId, Comment newComment) throws SQLException {
        log.debug("addComment input values: task id {}, newComment {}", taskId, newComment);
        var task = getTask(taskId);
        if (task == null) {
            log.error("Task with id {} not found", taskId);
            throw new NullPointerException();
        } else {
            log.debug("Task was found:{}", task);
        }
        return this.addComment(task, newComment);
    }

    @Override
    public Task addComment(Task task, Comment newComment) throws SQLException {
        log.debug("addComment input values: task {}, new Comment {}", task, newComment);
        task.getComments().add(newComment);
        taskDao.update(task);
        commentDao.insert(newComment);
        return task;
    }

    @Override
    public Comment removeComment(Long id) throws SQLException {
        log.debug("removeComment input values:{}", id);
        Comment comment;
        comment = commentDao.getById(id);
        if (comment == null) {
            log.error("Comment with id {} not found", id);
            throw new NullPointerException();
        } else {
            commentDao.delete(comment);
        }
        return comment;
    }

    @Override
    public Comment removeComment(Comment comment) throws SQLException {
        log.debug("removeComment input values:{}", comment);
        return this.removeComment(comment.getId());
    }

    @Override
    public Comment updateComment(Comment freshComment) throws SQLException {
        log.debug("updateComment input values:{}", freshComment);
        if (freshComment == null) {
            throw new NullPointerException("Comment cannot be null");
        } else {
            log.debug("UpdateComment {} was update", freshComment);
            commentDao.update(freshComment);
        }
        return freshComment;
    }

    @Override
    public Task setDueDate(Task task, LocalDateTime time) throws SQLException {
        return this.setDueDate(task.getId(), time);
    }

    @Override
    public Task setDueDate(Long taskId, LocalDateTime time) throws SQLException {
        log.debug("setDueDate input values: taskId {}, new time {}", taskId, time);
        var task = getTask(taskId);
        if (task == null) {
            log.error("Task with id {} not found", taskId);
            throw new NullPointerException();
        } else {
            task.setDueDate(time);
            this.updateTask(task);
        }
        return task;
    }

    @Override
    public Task updateDueDate(Task task, LocalDateTime time)  throws SQLException{
        log.debug("update due date:{} time for task: {}", time, task);
        if(task == null){
            throw new NullPointerException("task must not be null");
        }
        return this.updateDueDate(task.getId(), time);
    }

    @Override
    public Task updateDueDate(Long taskId, LocalDateTime time) throws SQLException{
        log.debug("update due date:{} time for task id: {}", time, taskId);
        Task freshTask = this.getTask(taskId);
        if(freshTask == null){
            log.error("due date was not updated for task");
            return null;
        }
        freshTask.setDueDate(time);
        return this.updateTask(freshTask);
    }

    @Override
    public Task setPriority(Task task, Priority priority) throws SQLException{
        log.debug("update priority:{}  for task: {}", priority, task);
        if(task == null){
            throw new NullPointerException("task must not be null");
        }
        Task freshTask = this.getTask(task.getId());
        if(freshTask == null){
            log.error("priority was not updated for task");
            return null;
        }
        freshTask.setPriority(priority);
        return this.updateTask(freshTask);
    }

    @Override
    public Task setParentTask(Task parent, Task current) throws SQLException{
        log.debug("update parent task:{}  for task: {}", parent, current);
        if(current == null){
            throw new NullPointerException("current task must not be null");
        }
        Task freshTask = this.getTask(current.getId());
        if(freshTask == null){
            log.error("current task was not found");
            return null;
        }
        if(parent != null){
            //refresh parent
            parent = this.getTask(parent);
        }
        freshTask.setParent(parent);
        return this.updateTask(freshTask);
    }

    @Override
    public Task moveTaskToRoot(Task task) throws SQLException{
        log.debug("move  task:{}  to root", task);
        return this.setParentTask(null, task);
    }

    @Override
    public Task toPauseState(Task task) throws SQLException{
        log.debug("change state for task:{} to PAUSE", task);
        return this.setState(task, Status.PAUSE);
    }

    @Override
    public Task setState(Task task, Status newState) throws SQLException{
        log.debug("set state:{}  for task: {}", newState, task);
        if(task == null){
            throw new NullPointerException("task must not be null");
        }
        Task freshTask = this.getTask(task.getId());
        if(freshTask == null){
            log.error("state was not updated for task");
            return null;
        }
        freshTask.setStatus(newState);
        return this.updateTask(freshTask);
    }
}
