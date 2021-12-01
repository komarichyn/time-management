package com.jc.tm.converter;

import com.jc.tm.db.entity.Comment;
import com.jc.tm.db.entity.Task;
import com.jc.tm.service.CommentDto;
import com.jc.tm.service.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Component
public class Converter {

    private String dateConverter(LocalDateTime time) {
        log.debug("dateConverter method. Input values:{}", time);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String date = time.format(dateTimeFormatter);
        return date;
    }

    public TaskDto taskToTaskDto(Task task) {
        log.debug("taskToTaskDto method. Input values:{}", task);
        TaskDto taskDto = new TaskDto();
        LocalDateTime created = task.getCreated();
        LocalDateTime dueDate = task.getDueDate();
        if (created != null) {
            String formattedDateCreated = dateConverter(created);
            taskDto.setCreated(formattedDateCreated);
        }
        if (dueDate != null) {
            String formattedDateDueDate = dateConverter(dueDate);
            taskDto.setDueDate(formattedDateDueDate);
        }
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setPriority(task.getPriority());
        taskDto.setComments(parsingCommentDataToCommentDTO(task.getComments()));
        taskDto.setProgress(task.getProgress());
        if (task.getProjects() != null) {
            taskDto.setProjectName(task.getProjects().getName());
        }
        return taskDto;
    }

    public Collection<TaskDto> parsingTaskDataToTaskDTO(Collection<Task> tasks) {
        log.debug("parsingTaskDataToTaskDTO method.");
        Collection<TaskDto> taskResult = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto taskDto = taskToTaskDto(task);
            taskResult.add(taskDto);
        }
        return taskResult;
    }

    private CommentDto commentToCommentDto(Comment comment) {
        log.debug("commentToCommentDto method. Input values:{}", comment);
        CommentDto commentDto = new CommentDto();
        LocalDateTime dateCreated = comment.getCreated();
        if (dateCreated != null) {
            String created = dateConverter(dateCreated);
            commentDto.setCreated(created);
        }
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    private Collection<CommentDto> parsingCommentDataToCommentDTO(Collection<Comment> comments) {
        log.debug("parsingCommentDataToCommentDTO method.");
        Collection<CommentDto> commentResult = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDto commentDto = commentToCommentDto(comment);
            commentResult.add(commentDto);
        }
        return commentResult;
    }
}