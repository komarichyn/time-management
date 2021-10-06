package com.jc.tm.Converter;

import com.jc.tm.db.entity.Comment;
import com.jc.tm.db.entity.Task;
import com.jc.tm.service.CommentDto;
import com.jc.tm.service.TaskDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class Converter {

    private String dateConverter(LocalDateTime time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String date = time.format(dateTimeFormatter);
        return date;
    }

    public TaskDto TasktoTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        LocalDateTime created = task.getCreated();
        LocalDateTime dueDate = task.getDueDate();
        if (created != null) {
            String formattedDateCreated = dateConverter(created);
            taskDto.setCreated(formattedDateCreated);
        }
        String formattedDateDueDate = dateConverter(created);
        taskDto.setDueDate(formattedDateDueDate);
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setPriority(task.getPriority());

        return taskDto;
    }

    private CommentDto CommentToCommentDto(Comment comment) {
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

    public Collection<TaskDto> parsingTaskDataToTaskDTO(Collection<Task> tasks) {
        Collection<TaskDto> taskResult = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto taskDto = TasktoTaskDto(task);
            taskResult.add(taskDto);
        }
        return taskResult;
    }

    public Collection<CommentDto> parsingCommentDataToCommentDTO(Collection<Comment> comments) {
        Collection<CommentDto> commentResult = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDto commentDto = CommentToCommentDto(comment);
            commentResult.add(commentDto);
        }
        return commentResult;
    }
}
