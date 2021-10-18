package com.jc.tm.controller;

import com.jc.tm.Converter.Converter;
import com.jc.tm.db.entity.Comment;
import com.jc.tm.db.Status;
import com.jc.tm.db.entity.Task;
import com.jc.tm.service.CommentDto;
import com.jc.tm.service.PaginationDto;
import com.jc.tm.service.TaskDto;
import com.jc.tm.service.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
                           String search,
                           @PathVariable(value = "pageNumber") int pageNumber,
                           @RequestParam(name="sortBy", required = false) String sortBy) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setSize(10);
        paginationDto.setPage(pageNumber);

        Page<Task> page = service.loadTask(paginationDto, sortBy);
        Collection<Task> taskList = page.getContent();
        Collection<TaskDto> result = converter.parsingTaskDataToTaskDTO(taskList);

        if(sortBy != null) {
            taskList = page.getContent();
            result = converter.parsingTaskDataToTaskDTO(taskList);
        }

        if(search != null) {
            page = service.findByKeyword(paginationDto, search);
            taskList = page.getContent();
            result = converter.parsingTaskDataToTaskDTO(taskList);
        }

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("searchBy",search);
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
    public Task updateTask(@PathVariable long taskId, @RequestBody String status) {
        Task task = service.getTask(taskId);
        status = status.replace("=",""); //delete sign '=' from String
        System.out.println(status);
        task.setStatus(Status.valueOf(status));
        service.updateTask(task);
        return task;
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
