package com.jc.tm.controller;

import com.jc.tm.Converter.Converter;
import com.jc.tm.controller.builder.CommentBuilder;
import com.jc.tm.controller.builder.CommentBuilderImpl;
import com.jc.tm.controller.builder.TaskBuilderImpl;
import com.jc.tm.db.Status;
import com.jc.tm.db.entity.Comment;
import com.jc.tm.db.entity.Task;
import com.jc.tm.service.Priority;
import com.jc.tm.service.TaskDto;
import com.jc.tm.service.TaskServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(Dashboard.class)
class DashboardTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskServiceImpl taskServiceMock;
    @MockBean
    private Converter converter;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new Dashboard(taskServiceMock, converter)).build();
    }

    private String dateConverter(LocalDateTime time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String date = time.format(dateTimeFormatter);
        return date;
    }


    @Test
    void shouldGetTaskById() throws Exception {
        Comment comment1 = new CommentBuilderImpl().setId(1L).setText("comment 1").setCreated(LocalDateTime.now()).build();
        Comment comment2 = new CommentBuilderImpl().setId(2L).setText("comment 2").setCreated(LocalDateTime.now()).build();
        List<Comment> comments = Arrays.asList(comment1, comment2);

        Task foundTask = new TaskBuilderImpl().setId(1L).setName("test").setDescription("some description")
                .setStatus(Status.TODO).setPriority(Priority.NORMAL)
                .setCreated(LocalDateTime.of(2021, 10, 19, 12, 25, 25))
                .setDueDate(LocalDateTime.of(2021, 11, 19, 12, 25, 25))
                .setComments(comments)
                .build();

        TaskDto taskDto = new Converter().TaskToTaskDto(foundTask);

        when(taskServiceMock.getTask(1L)).thenReturn(foundTask);
        when(converter.TaskToTaskDto(foundTask)).thenReturn(taskDto);

        mockMvc.perform(get("/task/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("task"))
                .andExpect(model().attribute("task", hasProperty("id", is(1L))))
                .andExpect(model().attribute("task", hasProperty("name", is("test"))))
                .andExpect(model().attribute("task", hasProperty("description", is("some description"))))
                .andExpect(model().attribute("task", hasProperty("status", is(Status.TODO))))
                .andExpect(model().attribute("task", hasProperty("priority", is(Priority.NORMAL))))
                .andExpect(model().attribute("task", hasProperty("created", is(dateConverter(LocalDateTime.of(2021, 10, 19, 12, 25, 25))))))
                .andExpect(model().attribute("task", hasProperty("dueDate", is(dateConverter(LocalDateTime.of(2021, 11, 19, 12, 25, 25))))));

        verify(taskServiceMock, times(1)).getTask(1L);
        verifyNoMoreInteractions(taskServiceMock);

    }

}