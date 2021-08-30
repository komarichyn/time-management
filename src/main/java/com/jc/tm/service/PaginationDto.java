package com.jc.tm.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto {
    //This line commented because in class TaskSubMenu in line 60 mistake
//    public static PaginationDto DEFAULT = new PaginationDto(0, 10, 1);
    private int index;
    private int size;
    private int page;
    private Sort.Direction sorDirectionASC = Sort.Direction.ASC;
    private Sort.Direction sorDirectionDESC = Sort.Direction.DESC;
    private String sortByName = "name";
    private String sortByDescription = "description";

}
