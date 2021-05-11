package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Comment;
import java.util.List;

public interface CommentDao extends BaseDao<Comment> {

    @Override
    void insert(Comment comment);

    @Override
    void update(Comment comment);

    @Override
    Comment getById(int id);

    @Override
    List<Comment> getAll();

    @Override
    boolean delete(Comment comment);
}
