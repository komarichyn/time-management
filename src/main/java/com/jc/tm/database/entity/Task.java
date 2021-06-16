package com.jc.tm.database.entity;

import com.jc.tm.database.Status;
import com.jc.tm.service.Priority;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Task {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime created;
    private List<Comment> comments = new ArrayList<>();
    private Status status;
    private Task parent;
    private LocalDateTime dueDate;
    //default prioritty for any task is normal
    private Priority priority = Priority.NORMAL;

}
