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
        newTask.setCreated(LocalDateTime.now());
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
            oldTask.setProgress(freshTask.getProgress());
            if (freshTask.getProgress() >= 10 && oldTask.getStatus() == Status.TODO) {
                oldTask.setStatus(Status.IN_PROGRESS);
            }
            if (freshTask.getProgress() == 100) {
                if (oldTask.getStatus() == Status.IN_PROGRESS || oldTask.getStatus() == Status.PAUSE) {
                    oldTask.setStatus(Status.COMPLETE);
                }
            }
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
    public Page<Task> loadTask(PaginationDto paginationDto, String searchBy, String sortBy) {
        log.debug("loadTasks method with paginationDto={} searchBy={} sortBy={}", paginationDto, searchBy, sortBy);
        searchBy = this.checkSearchBy(searchBy);
        sortBy = this.checkSortBy(sortBy);
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Page<Task> pt = taskDao.findAllBy(PageRequest.of(paginationDto.getIndex() - 1, paginationDto.getSize(), sort), searchBy);
        return pt;
    }

    @Override
    public Collection<Task> loadTasks() {
        log.debug("load tasks with default pagination");
        return this.taskDao.findAll();
    }

    @Override
    public Collection<Task> loadTasks(PaginationDto page) {
        log.debug("load task by pagination: {}", page);
        Page<Task> pt = taskDao.findAll(PageRequest.of(page.getPage(), page.getSize()));
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
            newComment.setCreated(LocalDateTime.now());
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

    public String sortedByNameASCTasks() {
        log.debug("sortedByNameASCTasks");
        return ("name");
    }

    public String sortedByStatusASCTasks() {
        log.debug("sortedByStatusASCTasks");
        return ("status");
    }

    public String sortedByDueDateASCTasks() {
        log.debug("sortedByDueDateASCTasks");
        return "due_date";
    }

    public Collection<Task> sortedByDueDateDESCTasks(PaginationDto paginationDto) {
        log.debug("sortedByNameDESCTasks input values: {}", paginationDto);
        return this.sortedBy(paginationDto, "dueDate");
    }

    public String sortedByPriorityASCTasks() {
        log.debug("sortedByPriorityASCTasks");
        return ("priority");
    }

    @Override
    public Collection<Task> sortedBy(PaginationDto paginationDto, String sortBy) {
        log.debug("sortedByNameASCTasks input values: {}", paginationDto);
        Sort sort = Sort.by(paginationDto.getSorDirectionASC(), sortBy);
        Page<Task> pt = taskDao.findAll(PageRequest.of(paginationDto.getPage(), paginationDto.getSize(), sort));
        return pt.getContent();
    }

    private String checkSortBy(String sortBy) {
        log.debug("Check sortBy={}", sortBy);
        if (sortBy == null || sortBy.isBlank()) {
            return this.sortedByNameASCTasks();
        } else if (sortBy.equals("status")) {
            return this.sortedByStatusASCTasks();
        } else if (sortBy.equals("due_date")) {
            return this.sortedByDueDateASCTasks();
        } else if (sortBy.equals("priority")) {
            return this.sortedByPriorityASCTasks();
        }
        return sortBy;
    }

    private String checkSearchBy(String searchBy) {
        log.debug("Check searchBy={}", searchBy);
        if (searchBy == null || searchBy.isBlank()) {
            return "";
        }
        return searchBy;
    }
}
