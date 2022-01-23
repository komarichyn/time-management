package com.jc.tm.dto;

import com.jc.tm.db.entity.Task;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String text;
    private String created;

}
