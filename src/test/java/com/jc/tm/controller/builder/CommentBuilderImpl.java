package com.jc.tm.controller.builder;

import com.jc.tm.db.entity.Comment;

import java.time.LocalDateTime;

public class CommentBuilderImpl implements CommentBuilder {
    Comment comment = new Comment();

    @Override
    public CommentBuilder setId(Long id) {
        comment.setId(id);
        return this;
    }

    @Override
    public CommentBuilder setText(String text) {
        comment.setText(text);
        return this;
    }

    @Override
    public CommentBuilder setCreated(LocalDateTime created) {
        comment.setCreated(created);
        return this;
    }

    @Override
    public Comment build() {
        return comment;
    }
}
