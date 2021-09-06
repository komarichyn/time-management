package com.jc.tm.controller;

import com.jc.tm.db.entity.Task;
import com.jc.tm.filter.ConvertDateFilter;
import com.jc.tm.service.PaginationDto;
import com.jc.tm.service.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * this class is controller and it merge database with UI
 */

@Slf4j
@Controller
public class Dashboard {
    private final TaskServiceImpl service;

    public Dashboard(TaskServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String mainPage(Model model) {
        log.debug("show last five tasks");
        PaginationDto paginationDto = new PaginationDto();
        model.addAttribute("lastFive", service.loadTasks(paginationDto));
        return "index.html";
    }

    @GetMapping("/show-tasks")
    public String show(Model model) {
        log.debug("show tasks page");
        PaginationDto paginationDto = new PaginationDto();
        model.addAttribute("service", service.loadTasks(paginationDto));
        return "show-tasks";
    }

    @GetMapping("/create-task")
    public String create(Model model) {
        log.debug("create task page");
        return "create-task";
    }

    @PostMapping("/add-task")
    public String createTask(@ModelAttribute Task task) {
        service.saveTask(task); //TODO fix bug with date
        return "redirect:/create-task";
    }

    @GetMapping("/update-task")
    public String update(Model model) {
        log.debug("update task page");
        return "update-task";
    }

    @GetMapping("/find-task")
    public String find(Model model) {
        log.debug("find task page");
        return "find-task";
    }

    @GetMapping("/delete-task")
    public String delete(Model model) {
        log.debug("delete task page");
        return "delete-task";
    }
}
