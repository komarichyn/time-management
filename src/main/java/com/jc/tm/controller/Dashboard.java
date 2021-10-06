package com.jc.tm.controller;

import com.jc.tm.Converter.Converter;
import com.jc.tm.db.entity.Task;
import com.jc.tm.service.*;
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

    @GetMapping("/show-tasks")
    public String show(Model model, String search, @RequestParam(name = "sortBy", required = false) String sortBy) {
        log.debug("show tasks page");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPage(0);
        paginationDto.setSize(20);
        Collection<Task> taskList = service.sortedByNameASCTasks(paginationDto);
        Collection<TaskDto> result = converter.parsingTaskDataToTaskDTO(taskList);
        if (sortBy != null) {
            taskList = service.sortedBy(paginationDto, sortBy);
            result = converter.parsingTaskDataToTaskDTO(taskList);
            model.addAttribute("sortBy", result);
        }
        if (search != null) {
            taskList = service.findByKeyword(search);
            result = converter.parsingTaskDataToTaskDTO(taskList);
            model.addAttribute("service", result);
        }
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
        TaskDto taskDto = converter.TasktoTaskDto(task);
        Collection<CommentDto> comments = converter.parsingCommentDataToCommentDTO(task.getComments());
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
        return "redirect:/show-tasks";
    }
}
