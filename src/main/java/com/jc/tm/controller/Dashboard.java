package com.jc.tm.controller;

import com.jc.tm.Converter.Converter;
import com.jc.tm.db.Status;
import com.jc.tm.db.entity.Comment;
import com.jc.tm.db.entity.Task;
import com.jc.tm.service.CommentDto;
import com.jc.tm.service.PaginationDto;
import com.jc.tm.service.TaskDto;
import com.jc.tm.service.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * this class is controller and it merge database with UI
 */

@Slf4j
@Controller
public class Dashboard {
    private final int PageSize = 10;

    @Autowired
    private TaskServiceImpl service;
    @Autowired
    private Converter converter;

    @GetMapping("/index")
    public String mainPage(Model model) {
        log.debug("show last five tasks");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPage(0);
        paginationDto.setSize(5);
        Collection<Task> taskList = service.sortedByDueDateDESCTasks(paginationDto);
        Collection<TaskDto> result = converter.parsingTaskDataToTaskDTO(taskList);
        model.addAttribute("lastFive", result);
        return "index";
    }

    @GetMapping("show-tasks/page/{pageNumber}")
    public String show(Model model,
                           String searchBy,
                           @PathVariable(value = "pageNumber") int pageNumber,
                           @RequestParam(name="sortBy", required = false) String sortBy) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setIndex(pageNumber);
        paginationDto.setSize(PageSize);

        var taskList = service.loadTask(paginationDto, searchBy, sortBy);
        int tm = (int) taskList.getTotalElements();
        var result = converter.parsingTaskDataToTaskDTO(taskList.getContent());

        paginationDto.setPage((int) (Math.ceil((double) tm / PageSize)));

        model.addAttribute("currentPage", paginationDto.getIndex());
        model.addAttribute("totalPages", paginationDto.getPage());
        model.addAttribute("totalItems", paginationDto.getSize());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("searchBy",searchBy);
        model.addAttribute("service", result);
        return "show-tasks";
    }

    @GetMapping("/create-task")
    public String create(Model model) {
        log.debug("create task page");
        return "create-task";
    }

    @PostMapping("/add-task")
    public String createTask(@ModelAttribute Task task) {
        service.saveTask(task);
        Long taskId = task.getId();
        return "redirect:/task/" + taskId;
    }

    @GetMapping("/task/{taskId}")
    public String getTaskById(Model model, @PathVariable long taskId) {
        Task task = service.getTask(taskId);
        TaskDto taskDto = converter.TaskToTaskDto(task);
        Collection<CommentDto> comments = taskDto.getComments();
        model.addAttribute("task", taskDto);
        model.addAttribute("comments", comments);
        return "task";
    }

    @GetMapping(value = {"/task/edit/{taskId}"})
    public String showEditTask(Model model, @PathVariable long taskId) {
        Task task = service.getTask(taskId);
        model.addAttribute("task", task);
        return "update-task";
    }

    @PostMapping(value = {"show-tasks/task/update/{taskId}"})
    public String updateTaskStatus(@PathVariable long taskId, @RequestBody String status) {
        log.debug("update Task Status: {}" + status);
        Task task = service.getTask(taskId);
        task.setStatus(Status.valueOf(status));
        service.updateTask(task);
        return "show-tasks";
    }

    @PostMapping(value = {"/task/update/{taskId}"})
    public String updateTask(@PathVariable long taskId, @ModelAttribute Task task) {
        task.setId(taskId);
        service.updateTask(task);
        return "redirect:/task/" + task.getId();
    }

    @GetMapping("/delete-task/{taskId}")
    public String deleteTask(@PathVariable long taskId) {
        log.debug("delete task");
        service.removeTask(taskId);
        return "redirect:/show-tasks/page/1";
    }

    //Comment controllers

    @PostMapping("/task/{taskId}/added-comment")
    public String addComment(@ModelAttribute("comment") Comment comment, @PathVariable("taskId") long taskId) {
        service.addComment(taskId,comment);
        return "redirect:/task/" + taskId;
    }

    @PostMapping("/task/{taskId}/edit-comment/{commentId}")
    public String editComment(@ModelAttribute("comment") Comment comment,
                              @PathVariable("commentId") long commentId,
                              @PathVariable("taskId") long taskId) {
        comment.setId(commentId);
        service.updateComment(comment);
        return "redirect:/task/" + taskId;
    }


    @GetMapping("/task/{taskId}/comment-del/{commentId}")
    public String deleteComment(@PathVariable("commentId") long commentId, @PathVariable("taskId") long taskId) {
        log.debug("delete comment");
        service.removeComment(commentId);
        return "redirect:/task/" + taskId;
    }
}
