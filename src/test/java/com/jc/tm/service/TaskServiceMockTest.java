package com.jc.tm.service;

import com.jc.tm.db.dao.jpa.CommentDao;
import com.jc.tm.db.dao.jpa.TaskDao;
import com.jc.tm.db.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.config.ConfigData;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceMockTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskDao taskDao;
    @Mock
    private CommentDao commentDao;


    @BeforeEach
    public void setUp() {

    }

    @Test
    @DisplayName("Test mock - Task service")
    public void testMethod() {
        assertThat(taskService).isNotNull();
    }

    @Test
    @DisplayName("Positive test for save task")
    public void saveTaskTest(){
        Task task = new Task();
        task.setId(1l);
        when(taskDao.save(any(Task.class))).thenReturn(task);

        task = taskService.saveTask(task);
        assertThat(task).isNotNull();
        assertNotNull(task.getId());
    }

    @Test
    @DisplayName("Negative test for remove task")
    public void removeTaskNegativeTest(){
        Task task = new Task();
        task.setId(1L);
        when(taskDao.findById(anyLong())).thenReturn(Optional.empty());
        task = taskService.removeTask(2L);
        assertNull(task);
    }

    @Test
    @DisplayName("Positive test for remove task")
    public void removeTaskPositiveTest(){
        Task task = new Task();
        Long id = 1L;
        task.setId(id);
        when(taskDao.findById(id)).thenReturn(Optional.of(task));
        task = taskService.removeTask(id);
        assertNotNull(task);
    }
}
