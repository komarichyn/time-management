package com.jc.tm;

import com.jc.tm.database.dao.CommentDaoImpl;
import com.jc.tm.database.entity.Comment;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * this main class for launch a whole application
 */
public class Main {

    public static void main(String[] args) {
        //Jdbc.getConnection();
        //test the method insert
        Comment comment = new Comment();
        comment.setTaskId(1L);
        comment.setText("test 1005352452");
        comment.setCreated(LocalDateTime.now());
        CommentDaoImpl commentDao = new CommentDaoImpl();
        try {
            System.out.println(commentDao.insert(comment));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
