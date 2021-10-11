package com.jc.tm.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name =  "comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id", nullable=false)
    private Task task;
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

}
