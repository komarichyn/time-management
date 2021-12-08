package com.jc.tm.service;

import com.jc.tm.db.Status;
import com.jc.tm.service.project.ProjectDto;
import lombok.Data;

import java.util.Collection;

@Data
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private String created;
    private Collection<CommentDto> comments;
    private Status status;
    private String dueDate;
    private Priority priority = Priority.NORMAL;
    private int progress;
    private ProjectDto projectName;
}
