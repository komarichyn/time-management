package com.jc.tm.dto;

import com.jc.tm.db.entity.Task;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String name;
}
