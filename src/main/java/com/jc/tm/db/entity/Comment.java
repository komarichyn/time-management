package com.jc.tm.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long taskId;
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

}
