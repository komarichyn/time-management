package com.jc.tm.controller;

import com.jc.tm.db.entity.Task;
import com.jc.tm.service.PaginationDto;
import com.jc.tm.service.TaskDto;
import com.jc.tm.service.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

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
        paginationDto.setPage(1);
        paginationDto.setSize(5);
        model.addAttribute("lastFive", service.loadTasks(paginationDto));
        return "index";
    }

    @GetMapping("/show-tasks")
    public String show(Model model) {
        log.debug("show tasks page");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPage(1);
        paginationDto.setSize(5);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Collection<Task> taskList = service.loadTasks(paginationDto);
        Collection<TaskDto> result = new ArrayList<>();
        for(Task task:taskList) {
            TaskDto taskDto = new TaskDto();
            LocalDateTime date = task.getDueDate();
            String formatedDate = date.format(dateTimeFormatter);
            taskDto.setDueDate(formatedDate);
            taskDto.setName(task.getName());
            taskDto.setPriority(task.getPriority());
            result.add(taskDto);
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
