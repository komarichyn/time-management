package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentDao extends BaseDao<Comment> {

    @Override
    void insert(Comment comment) throws SQLException;

    @Override
    void update(Comment comment) throws SQLException;

    @Override
    Comment getById(Long id) throws SQLException;

    @Override
    List<Comment> getAll() throws SQLException;

    @Override
    boolean delete(Comment comment) throws SQLException;
}
