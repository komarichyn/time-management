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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Mockito.when(taskDao.save(Mockito.any(Task.class))).thenReturn(task);

        task = taskService.saveTask(task);
        assertThat(task).isNotNull();
        assertNotNull(task.getId());
    }
}
