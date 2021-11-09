package com.jc.tm.builder;

import com.jc.tm.db.entity.Comment;

import java.time.LocalDateTime;

public interface CommentBuilder {
    CommentBuilder setId(Long id);
    CommentBuilder setText(String text);
    CommentBuilder setCreated(LocalDateTime created);
    Comment build();

}
