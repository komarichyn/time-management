package com.jc.tm.service.project;

import com.jc.tm.db.dao.jpa.ProjectDao;
import com.jc.tm.db.entity.Project;
import com.jc.tm.db.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Project saveProject(/*Task task, */Project project) {
        log.info("saveProject input values:{}", project);
        /*if(project.getTasks() == null) {
            List<Task> tasks = new ArrayList<>();
            tasks.add(task);
            project.setTasks(tasks);
        } else {
            project.getTasks().add(task);
        }
        task.setProjects(project);*/
        project = projectDao.save(project);
        return project;
    }

    @Override
    public Project removeProject(Long id) {
        var project = this.getProject(id);
        projectDao.delete(project);
        return project;
    }

    @Override
    public Project getProject(Long id) {
        return projectDao.findById(id).orElse(null);
    }

    @Override
    public List<Project> loadProject() {
        return projectDao.findAll();
    }
}
