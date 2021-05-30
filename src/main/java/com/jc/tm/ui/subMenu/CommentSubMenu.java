package com.jc.tm.ui.subMenu;

import com.jc.tm.database.entity.Comment;
import com.jc.tm.service.ITaskService;
import com.jc.tm.ui.console.MyDevice;

import java.sql.SQLException;

public class CommentSubMenu {
    private MyDevice console;
    private ITaskService service;

    public CommentSubMenu(MyDevice console, ITaskService service) {
        this.console = console;
        this.service = service;
    }

    public void createComment() {
        var comment = new Comment();
        console.printf("set task id%n");
        Long taskId = Long.parseLong(console.readLine());
        console.printf("enter a comment%n");
        String text = console.readLine();
        comment.setTaskId(taskId);
        comment.setText(text);
        try {
            service.addComment(taskId, comment);
        } catch (SQLException e) {
            console.printf("wrong input ");
        }
    }

    public void updateComment() {
        var comment = new Comment();
        console.printf("id of the comment we want to update%n");
        Long id = Long.parseLong(console.readLine());
        console.printf("enter a new comment%n");
        String newText = console.readLine();
        comment.setId(id);
        comment.setText(newText);
        try {
            service.updateComment(comment);
        } catch (SQLException e) {
            console.printf("wrong input ");
        }
    }
}
