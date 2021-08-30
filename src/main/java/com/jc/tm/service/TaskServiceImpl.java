package com.jc.tm.service;

import com.jc.tm.db.Status;
import com.jc.tm.db.dao.jpa.CommentDao;
import com.jc.tm.db.dao.jpa.TaskDao;
import com.jc.tm.db.entity.Comment;
import com.jc.tm.db.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * this TaskServiceImpl class cooperate with DAO of Task and Comments
 */
@Slf4j
@Service
public class TaskServiceImpl implements ITaskService {

    private final TaskDao taskDao;
    private final CommentDao commentDao;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao, CommentDao commentDao) {
        this.taskDao = taskDao;
        this.commentDao = commentDao;
    }

    @Override
    public Task saveTask(Task newTask) {
        log.debug("saveTask input values:{}", newTask);
        newTask = taskDao.save(newTask);
        log.info("new task {} was saved", newTask);
        return newTask;
    }

    @Override
    public Task removeTask(Long id) {
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
    public Task removeTask(Task task) {
        log.debug("removeTask input task:{}", task);
        return this.removeTask(task.getId());
    }

    @Override
    public Task updateTask(Task freshTask) {
        log.debug("updateTask input values:{}", freshTask);
        var oldTask = this.getTask(freshTask);
        if (oldTask == null) {
            log.error("This task {} not found", freshTask);
            throw new NullPointerException();
        } else {
            log.debug("This task {} was update", freshTask);
            oldTask.setName(freshTask.getName());
            oldTask.setPriority(freshTask.getPriority());
            oldTask.setStatus(freshTask.getStatus());
            oldTask.setDescription(freshTask.getDescription());
            oldTask.setDueDate(freshTask.getDueDate());
            taskDao.save(oldTask);
        }
        return freshTask;
    }

    @Override
    public Task getTask(Long id) {
        log.debug("getTask input values:{}", id);
        if (id == null) {
            log.error("Id {} not found", id);
            throw new NullPointerException();
        } else {
            return taskDao.findById(id).orElse(null);
        }
    }

    @Override
    public Task getTask(Task task) {
        log.debug("getTask input values:{}", task);
        return this.getTask(task.getId());
    }

    @Override
    public Collection<Task> loadTasks() {
        log.debug("load tasks with default pagination");
        return this.taskDao.findAll();
    }

    @Override
    public Collection<Task> loadTasks(PaginationDto page) {
        log.debug("load task by pagination: {}", page);
        Page<Task> pt = taskDao.findAll(PageRequest.of(0, 10));
        log.debug("result of call: {}", pt);
        return pt.getContent();
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
    public Task addComment(Task task, Comment newComment) {
        log.debug("addComment input values: task {}, new Comment {}", task, newComment);
        if (task.getComments() == null) {
            List<Comment> comments = new ArrayList<>();
            comments.add(newComment);
            task.setComments(comments);
        } else {
            task.getComments().add(newComment);
        }
        newComment.setTask(task);
        commentDao.save(newComment);
        return task;
    }

    @Override
    public Comment removeComment(Long id) {
        log.debug("removeComment input values:{}", id);
        Optional<Comment> optionalComment = commentDao.findById(id);
        if (optionalComment.isPresent()) {
            Comment c = optionalComment.get();
            commentDao.delete(optionalComment.get());
            return c;
        }
        return null;
    }

    @Override
    public Comment removeComment(Comment comment) {
        log.debug("removeComment input values:{}", comment);
        return this.removeComment(comment.getId());
    }

    @Override
    public Comment updateComment(Comment freshComment) {
        log.debug("updateComment input values:{}", freshComment);
        if (freshComment == null) {
            throw new NullPointerException("Comment cannot be null");
        } else {
            log.debug("UpdateComment {} was update", freshComment);
            Optional<Comment> com = commentDao.findById(freshComment.getId());
            if (com.isPresent()) {
                Comment comment = com.get();
                comment.setText(freshComment.getText());
                commentDao.save(comment);
            }
        }
        return freshComment;
    }

    @Override
    public Task setDueDate(Task task, LocalDateTime time) {
        return this.setDueDate(task.getId(), time);
    }

    @Override
    public Task setDueDate(Long taskId, LocalDateTime time) {
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
    public Task updateDueDate(Task task, LocalDateTime time) {
        log.debug("update due date:{} time for task: {}", time, task);
        if (task == null) {
            throw new NullPointerException("task must not be null");
        }
        return this.updateDueDate(task.getId(), time);
    }

    @Override
    public Task updateDueDate(Long taskId, LocalDateTime time) {
        log.debug("update due date:{} time for task id: {}", time, taskId);
        Task freshTask = this.getTask(taskId);
        if (freshTask == null) {
            log.error("due date was not updated for task");
            return null;
        }
        freshTask.setDueDate(time);
        return this.updateTask(freshTask);
    }

    @Override
    public Task setPriority(Task task, Priority priority) {
        log.debug("update priority:{}  for task: {}", priority, task);
        if (task == null) {
            throw new NullPointerException("task must not be null");
        }
        Task freshTask = this.getTask(task.getId());
        if (freshTask == null) {
            log.error("priority was not updated for task");
            return null;
        }
        freshTask.setPriority(priority);
        return this.updateTask(freshTask);
    }

    @Override
    public Task setParentTask(Task parent, Task current) {
        log.debug("update parent task:{}  for task: {}", parent, current);
        throw new RuntimeException("Not implemented yet");
//        if(current == null){
//            throw new NullPointerException("current task must not be null");
//        }
//        Task freshTask = this.getTask(current.getId());
//        if(freshTask == null){
//            log.error("current task was not found");
//            return null;
//        }
//        if(parent != null){
//            //refresh parent
//            parent = this.getTask(parent);
//        }
////        freshTask.setParent(parent);
//        return this.updateTask(freshTask);
    }

    @Override
    public Task moveTaskToRoot(Task task) {
        log.debug("move  task:{}  to root", task);
        return this.setParentTask(null, task);
    }

    @Override
    public Task toPauseState(Task task) {
        log.debug("change state for task:{} to PAUSE", task);
        return this.setState(task, Status.PAUSE);
    }

    @Override
    public Task setState(Task task, Status newState) {
        log.debug("set state:{}  for task: {}", newState, task);
        if (task == null) {
            throw new NullPointerException("task must not be null");
        }
        Task freshTask = this.getTask(task.getId());
        if (freshTask == null) {
            log.error("state was not updated for task");
            return null;
        }
        freshTask.setStatus(newState);
        return this.updateTask(freshTask);
    }

    public Collection<Task> sortedByNameASCTasks(PaginationDto paginationDto) {
        Sort sort = Sort.by(paginationDto.getSorDirectionASC(), paginationDto.getSortByName());
        Page<Task> pt = taskDao.findAll(PageRequest.of(0, 10, sort));
        return pt.getContent();
    }

    public Collection<Task> sortedByNameDESCTasks(PaginationDto paginationDto) {
        Sort sort = Sort.by(paginationDto.getSorDirectionDESC(), paginationDto.getSortByName());
        Page<Task> pt = taskDao.findAll(PageRequest.of(0, 10, sort));
        return pt.getContent();
    }
    /*public Page<Task> sortedByNameTasks(PaginationDto paginationDto) {
//        Sort sort = Sort.by(paginationDto.getSorDirection(), paginationDto.getSortBy());
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        Pageable pageable = PageRequest.of(0,10, sort);
        return taskDao.findAll(pageable);
    }*/
}
