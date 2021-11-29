package com.jc.tm.service.project;

import com.jc.tm.db.entity.Project;
import com.jc.tm.db.entity.Task;

import java.util.List;

public interface IProjectService {
    public Project saveProject(/*Task task, */Project newProject);

    Project removeProject(Long id);

    Project getProject(Long id);

    List<Project> loadProject();
}
