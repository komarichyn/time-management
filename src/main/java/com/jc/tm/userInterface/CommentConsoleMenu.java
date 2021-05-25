package com.jc.tm.userInterface;

import com.jc.tm.database.dao.CommentDao;
import com.jc.tm.database.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import java.sql.SQLException;
import java.util.Scanner;

@Slf4j
public class CommentConsoleMenu {
    private CommentDao commentDao;
    private Scanner sc;

    public CommentConsoleMenu(Scanner sc, CommentDao commentDao) {
        this.commentDao = commentDao;
        this.sc =sc;
    }

    public void addComment() {
        var comment = new Comment();
        System.out.println("Set task id: ");
        comment.setTaskId(sc.nextLong());
        sc.nextLine();
        System.out.println("Write comment text: ");
        comment.setText(sc.nextLine());
        try {
            commentDao.insert(comment);
        } catch (SQLException e) {
            log.error("wrong input",e);
        }
    }

    public void getAllComments(){

    }

}
