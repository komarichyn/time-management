package com.jc.tm.service;

import com.jc.tm.db.Status;
import com.jc.tm.db.entity.Comment;
import com.jc.tm.db.entity.Project;
import lombok.Data;

import java.util.Collection;
import java.util.List;

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
    private String projectName;
}
