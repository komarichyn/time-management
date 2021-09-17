package com.jc.tm.controller;

import com.jc.tm.db.dao.jpa.TaskDao;
import com.jc.tm.db.entity.Task;
import com.jc.tm.service.PaginationDto;
import com.jc.tm.service.TaskDto;
import com.jc.tm.service.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Dashboard(TaskServiceImpl service) {
        this.service = service;
    }

    private Collection<TaskDto> parsingTaskDataToTaskDTO(Collection<Task> tasks) {
        Collection<TaskDto> result = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto taskDto = dateConvert(task);
            result.add(taskDto);
        }
        return result;
    }

    private TaskDto dateConvert(Task task) {
        TaskDto taskDto = new TaskDto();
        LocalDateTime dateCreated = task.getCreated();
        LocalDateTime dateDueDate = task.getDueDate();
        if (dateCreated != null) {
            String formattedDateCreated = dateCreated.format(dateTimeFormatter);
            taskDto.setCreated(formattedDateCreated);
        }
        String formattedDateDueDate = dateDueDate.format(dateTimeFormatter);
        taskDto.setDueDate(formattedDateDueDate);
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setPriority(task.getPriority());
        return taskDto;
    }

    @GetMapping("/index")
    public String mainPage(Model model) {
        log.debug("show last five tasks");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPage(0);
        paginationDto.setSize(5);
        Collection<Task> taskList = service.sortedByDueDateDESCTasks(paginationDto);
        Collection<TaskDto> result = parsingTaskDataToTaskDTO(taskList);
        model.addAttribute("lastFive", result);
        return "index";
    }

    @GetMapping("/show-tasks")
    public String show(Model model, String search) {
        log.debug("show tasks page");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPage(0);
        paginationDto.setSize(20);
        Collection<Task> taskList = service.loadTasks(paginationDto);
        Collection<TaskDto> result = parsingTaskDataToTaskDTO(taskList);
        model.addAttribute("service", result);
        if(search != null) {
            taskList = service.findByKeyword(search);
            if(taskList.size() == 0) {

            }
            result = parsingTaskDataToTaskDTO(taskList);
            model.addAttribute("service", result);
        }
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

    @GetMapping("/task/{taskId}")
    public String getTaskById(Model model, @PathVariable long taskId) {
        Task task = service.getTask(taskId);
        TaskDto taskDto = dateConvert(task);
        model.addAttribute("task", taskDto);
        return "task";
    }

    @GetMapping("/update-task") // TODO update method
    public String update(Model model) {
        log.debug("update task page");
        return "update-task";
    }

    @GetMapping("/delete-task/{taskId}")
    public String deleteTask(@PathVariable long taskId) {
        log.debug("delete task");
        service.removeTask(taskId);
        return "redirect:/show-tasks";
    }
}
