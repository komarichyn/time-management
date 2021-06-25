package com.jc.tm.db.entity;

import com.jc.tm.db.Status;
import com.jc.tm.service.Priority;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created;
    @OneToMany(targetEntity = Comment.class)
    private List<Comment> comments;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dueDate;
    //default prioritty for any task is normal
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.NORMAL;

}
