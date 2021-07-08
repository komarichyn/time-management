package com.jc.tm.service;

import com.jc.tm.db.H2Config;
import com.jc.tm.db.Status;
import com.jc.tm.db.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {H2Config.class})
public class ITaskServiceTest {

    @Autowired
    private ITaskService taskService;

    @BeforeEach
    void setUp() {

    }

    private Task generateTask() {
        Task task = new Task();
        task.setName("test");
        task.setDescription("some description");
        task.setStatus(Status.TODO);
        task.setPriority(Priority.NORMAL);
        return task;
    }

    @Test
    void saveTask() {
        Object result = taskService.saveTask(generateTask());
        assertThat(result).isNotNull();
    }

    @Test
    void removeTask() {
    }

    @Test
    void removeTask1() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void getTask() {
    }

    @Test
    void getTask1() {
    }

    @Test
    void loadTasks() {
    }

    @Test
    void loadTasks1() {
    }

    @Test
    void loadTasksByDescPriority() {
    }

    @Test
    void loadTasksByAskPriority() {
    }

    @Test
    void addComment() {
    }

    @Test
    void addComment1() {
    }

    @Test
    void removeComment() {
    }

    @Test
    void removeComment1() {
    }

    @Test
    void updateComment() {
    }

    @Test
    void setDueDate() {
    }

    @Test
    void setDueDate1() {
    }

    @Test
    void updateDueDate() {
    }

    @Test
    void updateDueDate1() {
    }

    @Test
    void setPriority() {
    }

    @Test
    void setParentTask() {
    }

    @Test
    void moveTaskToRoot() {
    }

    @Test
    void toPauseState() {
    }

    @Test
    void setState() {
    }
}