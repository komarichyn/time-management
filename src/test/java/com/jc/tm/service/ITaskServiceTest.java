package com.jc.tm.service;

import com.jc.tm.db.Status;
import com.jc.tm.db.dao.jpa.CommentDao;
import com.jc.tm.db.dao.jpa.TaskDao;
import com.jc.tm.db.entity.Task;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ITaskServiceTest {

    @Mock
    private TaskDao taskDao;
    @Mock
    private CommentDao commentDao;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Positive test for save task")
    void saveTask() {
        //given
        Task task = new Task();
        task.setId(1L);
        task.setName("test");
        task.setDescription("some description");
        task.setStatus(Status.TODO);
        task.setPriority(Priority.NORMAL);
        //when
        when(taskDao.save(any(Task.class))).thenReturn(task);
        //then
        task = taskService.saveTask(task);
        assertThat(task).isNotNull();
        assertNotNull(task.getId());
    }

    @Test
    @DisplayName("Positive test for remove task")
    void removeTaskPositive() {
        Task task = new Task();
        task.setId(1L);
        when(taskDao.findById(anyLong())).thenReturn(Optional.empty());
        task = taskService.removeTask(task);
        assertNull(task);
    }

    @Test
    @DisplayName("Negative test for remove task")
    public void removeTaskNegativeTest() {
        Task task = new Task();
        task.setId(1L);
        when(taskDao.findById(anyLong())).thenReturn(Optional.empty());
        task = taskService.removeTask(2L);
        assertNull(task);
    }

    @Test
    @DisplayName("throw new NullPointerException() in id is null")
    public void throwsNullPointerIfIdIsNull() {
        Long id = null;
        assertThatThrownBy(() -> taskService.getTask(id)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Update task if exists")
    public void shouldUpdateTaskIfExists() {
        Task task = new Task();
        task.setId(1L);
        task.setName("updated task");
        when(taskDao.findById(anyLong())).thenReturn(Optional.of(task));
        task = taskService.updateTask(task);
        assertNotNull(task);
    }

    @Test
    void loadAllTasks() {
        //when
        taskService.loadTasks();
        //then
        verify(taskDao).findAll();
    }
}