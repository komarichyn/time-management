package com.jc.tm.database.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Comment {
    private Long id;
    private String text;
    private LocalDate created;
    private Task task;
}
