package com.jc.tm.service.impl;

import com.jc.tm.db.dao.jpa.ProjectDao;
import com.jc.tm.db.entity.Project;
import com.jc.tm.service.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProjectServiceImpl implements IProjectService {

    private final ProjectDao projectDao;

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Project saveProject(Project project) {
        project = projectDao.save(project);
        log.info("saveProject input values:{}", project);
        return project;
    }

    @Override
    public Project removeProject(Long id) {
        log.debug("removeProject input values:{}", id);
        var project = this.getProject(id);
        projectDao.delete(project);
        return project;
    }

    @Override
    public Project getProject(Long id) {
        log.debug("getProject input values:{}", id);
        return projectDao.findById(id).orElse(null);
    }

    @Override
    public List<Project> loadProject() {
        log.debug("loadProject");
        return projectDao.findAll();
    }
}
