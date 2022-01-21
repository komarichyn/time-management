package com.jc.tm.controller;

import com.jc.tm.converter.Converter;
import com.jc.tm.util.Status;
import com.jc.tm.db.entity.Comment;
import com.jc.tm.db.entity.Project;
import com.jc.tm.db.entity.Task;
import com.jc.tm.dto.CommentDto;
import com.jc.tm.dto.PaginationDto;
import com.jc.tm.dto.TaskDto;
import com.jc.tm.service.impl.TaskServiceImpl;
import com.jc.tm.dto.ProjectDto;
import com.jc.tm.service.impl.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * this class is controller and it merge database with UI
 */

@Slf4j
@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/time-management")

public class Dashboard {
    private final int pageSize = 10;

    private final TaskServiceImpl service;
    private final ProjectServiceImpl projectService;
    private final Converter converter;

    @Autowired
    public Dashboard(TaskServiceImpl service, ProjectServiceImpl projectService, Converter converter) {
        this.service = service;
        this.projectService = projectService;
        this.converter = converter;
    }

    //TODO - DONE
    @GetMapping
    public Collection<TaskDto> mainPage() {
        log.debug("Show last five tasks");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPage(0);
        paginationDto.setSize(5);
        Collection<Task> taskList = service.sortedByDueDateDESCTasks(paginationDto);
        Collection<TaskDto> result = converter.parsingTaskDataToTaskDTO(taskList);
        return result;
    }

    @GetMapping("show-tasks/page/{pageNumber}")
    public String show(Model model,
                       String searchBy,
                       @PathVariable(value = "pageNumber") int pageNumber,
                       @RequestParam(name = "sortBy", required = false) String sortBy) {
        log.debug("Show tasks page with params: searchBy={}, pageNumber={}, sortBy={}", searchBy, pageNumber, sortBy);
        SortnSearch sortnSearch = new SortnSearch(searchBy, sortBy);
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setIndex(pageNumber);
        paginationDto.setSize(pageSize);
        var taskList = service.loadTask(paginationDto, searchBy, sortBy);
        int tm = (int) taskList.getTotalElements();
        var result = converter.parsingTaskDataToTaskDTO(taskList.getContent());
        paginationDto.setPage((int) (Math.ceil((double) tm / pageSize)));
        model.addAttribute("pagination", paginationDto);
        model.addAttribute("sortSearch", sortnSearch);
        model.addAttribute("service", result);
        return "show-tasks";
    }

    //TODO - DONE
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        log.debug("Add task page. TaskTableRow={}", task);
        return service.saveTask(task);
    }

    //TODO - DONE
    @GetMapping("/task/{taskId}")
    public TaskDto getTaskById(@PathVariable long taskId) {
        log.debug("Show one task with id={}", taskId);
        Task task = service.getTask(taskId);
        return converter.taskToTaskDto(task);
    }

    @GetMapping(value = {"/task/edit/{taskId}"})
    public String showEditTask(Model model, @PathVariable long taskId) {
        log.debug("Change task with id={}", taskId);
        var projects = projectService.loadProject();
        Task task = service.getTask(taskId);
        model.addAttribute("task", task);
        model.addAttribute("projects", projects);
        return "update-task";
    }

    @PostMapping(value = {"show-tasks/task/update/{taskId}"}, produces = "application/json")
    public Task updateTaskStatus(@PathVariable long taskId, @RequestBody String status) {
        log.debug("Update TaskTableRow Status: {}" + status);
        Task task = service.getTask(taskId);
        task.setStatus(Status.valueOf(status));
        service.updateTask(task);
        return task;
    }

    @PostMapping(value = {"/task/update/{taskId}"})
    public String updateTask(@PathVariable long taskId, @ModelAttribute Task task) {
        log.debug("Update task={} with id={}", task, taskId);
        task.setId(taskId);
        service.updateTask(task);
        return "redirect:/task/" + task.getId();
    }

    //TODO - DONE
    @DeleteMapping("/delete-task/{taskId}")
    public void deleteTask(@PathVariable long taskId) {
        log.debug("Delete task with id={}", taskId);
        service.removeTask(taskId);
    }

    //Comment controllers

    @PostMapping("/task/{taskId}/added-comment")
    public String addComment(@ModelAttribute("comment") Comment comment, @PathVariable("taskId") long taskId) {
        log.debug("Add comment={} in task with id={}", comment, taskId);
        service.addComment(taskId,comment);
        return "redirect:/task/" + taskId;
    }

    @PostMapping("/task/{taskId}/edit-comment/{commentId}")
    public String editComment(@ModelAttribute("comment") Comment comment,
                              @PathVariable("commentId") long commentId,
                              @PathVariable("taskId") long taskId) {
        log.debug("Update comment with id={} in task with id={}", commentId, taskId);
        comment.setId(commentId);
        service.updateComment(comment);
        return "redirect:/task/" + taskId;
    }


    @GetMapping("/task/{taskId}/comment-del/{commentId}")
    public String deleteComment(@PathVariable("commentId") long commentId, @PathVariable("taskId") long taskId) {
        log.debug("Delete comment with id={} in task with id={}", commentId, taskId);
        service.removeComment(commentId);
        return "redirect:/task/" + taskId;
    }

    @PostMapping("/add-project")
    public Project addProject(@RequestBody Project project) {
        log.debug("Add project page. Project={}", project);
        return projectService.saveProject(project);
    }

    @GetMapping("/get-all-projects")
    public Collection<ProjectDto> loadProjects() {
        log.debug("loading all projects");
        Collection<ProjectDto> projectDtoList = converter.parsingProjectDataToProjectDTO(projectService.loadProject());
        return projectDtoList;
    }


}
