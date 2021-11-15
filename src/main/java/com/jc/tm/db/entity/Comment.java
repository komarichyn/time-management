package com.jc.tm.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public static class CommentBuilder {
        private Comment comment;

        public CommentBuilder() {
            comment = new Comment();
        }

        public CommentBuilder setId(Long id) {
            comment.id = id;
            return this;
        }

        public CommentBuilder setTask(Task task) {
            comment.task = task;
            return this;
        }

        public CommentBuilder setText(String text) {
            comment.text = text;
            return this;
        }

        public CommentBuilder setCreated(LocalDateTime created) {
            comment.created = created;
            return this;
        }

        public Comment build() {
            return comment;
        }
    }
}