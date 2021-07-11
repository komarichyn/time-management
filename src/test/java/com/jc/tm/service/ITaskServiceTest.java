package com.jc.tm.service;

import com.jc.tm.db.H2Config;
import com.jc.tm.db.Status;
import com.jc.tm.db.dao.jpa.CommentDao;
import com.jc.tm.db.dao.jpa.TaskDao;
import com.jc.tm.db.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = {H2Config.class})
/*
I commented this annotation because :
    If I understand right how mocks work
    than we don't need connection to db because we test service layer and we mocked the dao layer
    we will use H2Config when we test our oun custom methods in dao
    is that right?
 */
public class ITaskServiceTest {

    @Mock
    private TaskDao taskDao;
    @Mock
    private CommentDao commentDao;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskDao, commentDao);
    }

    @Test
    void saveTask() {
        //given
        Task task = new Task();
        task.setId(1L);
        task.setName("test");
        task.setDescription("some description");
        task.setStatus(Status.TODO);
        task.setPriority(Priority.NORMAL);
        //when
        Mockito.when(taskDao.save(Mockito.any(Task.class))).thenReturn(task);
        //then
        task = taskService.saveTask(task);
        assertThat(task).isNotNull();
        assertNotNull(task.getId());
    }

    @Test
    @Disabled
    //not working yet
    void removeTask(){
        Task task = new Task();
        task.setId(1L);
        taskService.removeTask(task);
        verify(taskService.removeTask(taskDao.getById(task.getId())));
    }

    @Test
    void loadAllTasks() {
        //when
        taskService.loadTasks();
        //then
        verify(taskDao).findAll();
    }



}