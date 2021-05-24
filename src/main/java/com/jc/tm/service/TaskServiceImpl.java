package com.jc.tm.service;

import com.jc.tm.database.Status;
import com.jc.tm.database.dao.CommentDao;
import com.jc.tm.database.dao.TaskDao;
import com.jc.tm.database.entity.Comment;
import com.jc.tm.database.entity.Task;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

public class TaskServiceImpl implements ITaskService {

    private TaskDao taskDao;
    private CommentDao commentDao;

    public TaskServiceImpl(TaskDao taskDao, CommentDao commentDao) {
        this.taskDao = taskDao;
        this.commentDao = commentDao;
    }

    @Override
    public Task saveTask(Task newTask) {
        Task insetredTask = null;
        try {
             insetredTask = taskDao.insert(newTask);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return insetredTask;
    }

    @Override
    public Task removeTask(Long id) {
        var task = getTask(id);
        try {
            taskDao.delete(task);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return task;
    }

    @Override
    public Task removeTask(Task task) {
        try {
            taskDao.delete(task);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return task;
    }

    @Override
    public Task updateTask(Task freshTask) {
        try {
            taskDao.update(freshTask);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return freshTask;
    }

    @Override
    public Task getTask(Long id) {
        Task taskById = null;
        try {
            taskById = taskDao.getById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return taskById;
    }

    @Override
    public Task getTask(Task task) {
        try {
            taskDao.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return task;
    }

    @Override
    public Collection<Task> loadTasks() {
        return null;
    }

    @Override
    public Collection<Task> loadTasks(PaginationDto page) {
        return null;
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
    public Task addComment(Long taskId, Comment newComment) {
        var task = getTask(taskId);
        return addCommnet(task, newComment);
    }

    @Override
    public Task addCommnet(Task task, Comment newComment) {
//        commentDao.insert()
        return null;
    }

    @Override
    public Comment removeComment(Long id) {
/*        var comment = null;
        try {
            comment = commentDao.getById(id);
            commentDao.delete(comment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        return null;
    }

    @Override
    public Comment removeComment(Comment comment) {
        try {
            commentDao.delete(comment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return comment;
    }

    @Override
    public Comment updateComment(Comment freshComment) {
        try {
            commentDao.update(freshComment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return freshComment;
    }

    @Override
    public Task setDueDate(Task task, LocalDateTime time) {

        return null;
    }

    @Override
    public Task setDueDate(Long taskId, LocalDateTime time) {
        var task = getTask(taskId);
        return setDueDate(task, time);
    }

    @Override
    public Task updateDueDate(Task task, LocalDateTime time) {
        return null;
    }

    @Override
    public Task updateDueDate(Long taskId, LocalDateTime time) {
        return null;
    }

    @Override
    public Task setPriority(Task task, Priority priority) {
        return null;
    }

    @Override
    public Task setParentTask(Task parent, Task current) {
        return null;
    }

    @Override
    public Task moveTaskToRoot(Task task) {
        return null;
    }

    @Override
    public Task toPauseState(Task task) {
        return null;
    }

    @Override
    public Task setState(Task task, Status newState) {
        return null;
    }
}
