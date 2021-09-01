package com.jc.tm.controller;

import com.jc.tm.service.PaginationDto;
import com.jc.tm.service.TaskServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dashboard {
    private final TaskServiceImpl service;

    public Dashboard(TaskServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/show-tasks")
    public String show(Model model) {
        PaginationDto paginationDto = new PaginationDto();
        service.loadTasks(paginationDto);
        model.addAttribute("service", service.loadTasks());
        return "show-tasks";
//        PaginationDto paginationDto = new PaginationDto();
        /*return new ResponseEntity<>(service.sortedByNameDESCTasks(paginationDto).stream().
                map(x->x.toString()).collect(Collectors.joining(", ")), HttpStatus.OK);*/
    }

    @GetMapping("/create-task")
    public String create(Model model) {
        model.addAttribute("myAtribute", "Petja");
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setSize(10);
        paginationDto.setSortByName("my Field");
        paginationDto.setPage(1);
        model.addAttribute("myModel", paginationDto);
        return "create-task";
    }

    @GetMapping("/update-task")
    public String update(Model model) {
        return "update-task";
    }

    @GetMapping("/find-task")
    public String find(Model model) {
        return "find-task";
    }

    @GetMapping("/delete-task")
    public String delete(Model model) {
        return "delete-task";
    }
}
