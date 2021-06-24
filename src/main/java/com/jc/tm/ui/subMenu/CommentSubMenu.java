package com.jc.tm.ui.subMenu;

import com.jc.tm.database.entity.Comment;
import com.jc.tm.service.ITaskService;
import com.jc.tm.ui.console.MyDevice;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class CommentSubMenu {
    private MyDevice console;
    private ITaskService service;

    public CommentSubMenu(MyDevice console, ITaskService service) {
        this.console = console;
        this.service = service;
    }

    public void createComment() {
        log.debug("createComment: in CommentSubMenu");
        var comment = new Comment();
        console.printf("set task id%n");
        Long taskId = Long.parseLong(console.readLine());
        console.printf("enter a comment%n");
        String text = console.readLine();
        comment.setTaskId(taskId);
        comment.setText(text);
        try {
            log.debug("createComment: comment from user {}", comment);
            service.addComment(taskId, comment);
        } catch (SQLException e) {
            log.error("createComment: problem with {}", comment);
            e.printStackTrace();
        }
    }

    public void updateComment() {
        log.debug("updateComment: in CommentSubMenu");
        var comment = new Comment();
        console.printf("id of the comment we want to update%n");
        Long id = Long.parseLong(console.readLine());
        console.printf("enter a new comment%n");
        String newText = console.readLine();
        comment.setId(id);
        comment.setText(newText);
        try {
            log.debug("updateComment: comment from user {}", comment);
            service.updateComment(comment);
        } catch (SQLException e) {
            log.error("updateComment: problem with {}" , comment);
            e.printStackTrace();
        }
    }

    public void removeComment() {
        log.debug("removeComment: in CommentSubMenu");
        console.printf("Enter comment id to delete: ");
        Long id = Long.parseLong(console.readLine());
        try {
            log.debug("removeComment: id from user {}", id);
            service.removeComment(id);
        } catch (SQLException e) {
            log.error("removeComment: problem with {}", id);
            e.printStackTrace();
        }
    }
}