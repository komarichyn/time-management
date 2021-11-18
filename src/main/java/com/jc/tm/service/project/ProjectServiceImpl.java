package com.jc.tm.service.project;

import com.jc.tm.db.dao.jpa.ProjectDao;
import com.jc.tm.db.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectServiceImpl implements IProjectService {

    private final ProjectDao projectDao;

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Project saveProject(Project newProject) {
        log.info("saveProject input values:{}", newProject);
        newProject = projectDao.save(newProject);
        return newProject;
    }
}
