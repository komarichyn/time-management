package com.jc.tm.controller;

import com.jc.tm.converter.Converter;
import com.jc.tm.db.Status;
import com.jc.tm.db.entity.Comment;
import com.jc.tm.db.entity.Project;
import com.jc.tm.db.entity.Task;
import com.jc.tm.service.CommentDto;
import com.jc.tm.service.PaginationDto;
import com.jc.tm.service.TaskDto;
import com.jc.tm.service.TaskServiceImpl;
import com.jc.tm.service.project.ProjectDto;
import com.jc.tm.service.project.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


/**
 * this class is controller and it merge database with UI
 */

@Slf4j
@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/")
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

    @GetMapping
    public Collection<TaskDto> mainPage() {
        log.debug("Show last five tasks");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPage(0);
        paginationDto.setSize(5);
        Collection<Task> taskList = service.sortedByDueDateDESCTasks(paginationDto);
        return converter.parsingTaskDataToTaskDTO(taskList);
    }

    @GetMapping("show-tasks/page/{pageNumber}")
    public Collection<TaskDto> show(String searchBy,
                                    @PathVariable(value = "pageNumber") int pageNumber,
                                    @RequestParam(name = "sortBy", required = false) String sortBy) {
        log.debug("Show tasks page with params: searchBy={}, pageNumber={}, sortBy={}", searchBy, pageNumber, sortBy);
//        SortnSearch sortnSearch = new SortnSearch(searchBy, sortBy);
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setIndex(pageNumber);
        paginationDto.setSize(pageSize);
        var taskList = service.loadTask(paginationDto, searchBy, sortBy);
//        int tm = (int) taskList.getTotalElements();
        var result = converter.parsingTaskDataToTaskDTO(taskList.getContent());

//        paginationDto.setPage((int) (Math.ceil((double) tm / pageSize)));
        return result;
    }

    @GetMapping("show-tasks/searchBy={searchBy}")
    public Collection<TaskDto> findByName2(@PathVariable String searchBy) {
        String sortBy = "";
        int pageNumber = 1;
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setIndex(pageNumber);
        paginationDto.setSize(pageSize);
        var taskList = service.loadTask(paginationDto, searchBy, sortBy);
        return converter.parsingTaskDataToTaskDTO(taskList.getContent());
    }

//    @GetMapping("show-tasks/{searchBy}")
    @GetMapping("show-tasks/searchBy={searchBy}")
    public Collection<TaskDto> findByName(@PathVariable String searchBy) {
        String sortBy = "";
        int pageNumber = 1;
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setIndex(pageNumber);
        paginationDto.setSize(pageSize);
        var taskList = service.loadTask(paginationDto, searchBy, sortBy);
        return converter.parsingTaskDataToTaskDTO(taskList.getContent());
    }

    @PostMapping("/create-task")
    public Task create(@RequestBody Task task) {
        log.debug("create task page");
        return service.saveTask(task);
    }

    @GetMapping("/task/{taskId}")
    public String getTaskById(Model model, @PathVariable long taskId) {
        log.debug("Show one task with id={}", taskId);
        Task task = service.getTask(taskId);
        TaskDto taskDto = converter.taskToTaskDto(task);
        Collection<CommentDto> comments = taskDto.getComments();
        model.addAttribute("task", taskDto);
        model.addAttribute("comments", comments);
        return "task";
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
        log.debug("Update Task Status: {}" + status);
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

    @GetMapping("/delete-task/{taskId}")
    public String deleteTask(@PathVariable long taskId) {
        log.debug("Delete task with id={}", taskId);
        service.removeTask(taskId);
        return "redirect:/show-tasks/page/1";
    }

    //Comment controllers

    @PostMapping("/task/{taskId}/added-comment")
    public String addComment(@ModelAttribute("comment") Comment comment, @PathVariable("taskId") long taskId) {
        log.debug("Add comment={} in task with id={}", comment, taskId);
        service.addComment(taskId, comment);
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
        return converter.parsingProjectDataToProjectDTO(projectService.loadProject());
    }
}