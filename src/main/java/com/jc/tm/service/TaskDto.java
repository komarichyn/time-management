package com.jc.tm.service;

import com.jc.tm.db.Status;
import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private String date;
    private String dueDate;
    private Priority priority = Priority.NORMAL;
}
