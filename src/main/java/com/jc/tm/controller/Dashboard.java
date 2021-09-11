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
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Dashboard(TaskServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String mainPage(Model model) {
        log.debug("show last five tasks");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPage(0);
        paginationDto.setSize(5);
        Collection<Task> taskList = service.sortedByDueDateDESCTasks(paginationDto);
        Collection<TaskDto> result = new ArrayList<>();
        for(Task task:taskList) {
            TaskDto taskDto = new TaskDto();
            LocalDateTime dateCreated = task.getCreated();
            LocalDateTime dateDueDate = task.getDueDate();
            String formatedDateCteated = dateCreated.format(dateTimeFormatter);
            String formatedDateDueDate = dateDueDate.format(dateTimeFormatter);
            taskDto.setName(task.getName());
            taskDto.setCreated(formatedDateCteated);
            taskDto.setStatus(task.getStatus());
            taskDto.setDueDate(formatedDateDueDate);
            taskDto.setPriority(task.getPriority());
            result.add(taskDto);
        }
        model.addAttribute("lastFive", result);
        return "index";
    }

    @GetMapping("/show-tasks")
    public String show(Model model) {
        log.debug("show tasks page");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPage(0);
        paginationDto.setSize(20);
        Collection<Task> taskList = service.loadTasks(paginationDto);
        Collection<TaskDto> result = new ArrayList<>();
        for(Task task:taskList) {
            TaskDto taskDto = new TaskDto();
//            LocalDateTime dateCreated = task.getCreated();
            LocalDateTime dateDueDate = task.getDueDate();
//            String formatedDateCteated = dateCreated.format(dateTimeFormatter);
            String formatedDateDueDate = dateDueDate.format(dateTimeFormatter);
            taskDto.setName(task.getName());
//            taskDto.setCreated(formatedDateCteated);
            taskDto.setStatus(task.getStatus());
            taskDto.setDueDate(formatedDateDueDate);
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
        service.saveTask(task);
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

    @PostMapping("/delete task")
    public String deleteTask(@ModelAttribute Task task) {
        log.debug("delete task");
        service.removeTask(task); //TODO removeTask from database
        return "redirect:/show-tasks";
    }
}
