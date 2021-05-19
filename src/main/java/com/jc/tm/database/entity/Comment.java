package com.jc.tm.database.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long taskId;
    private String text;
    private LocalDateTime created;

}
