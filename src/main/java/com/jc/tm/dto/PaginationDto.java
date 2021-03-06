package com.jc.tm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto {
    private int index;
    private int size;
    private int page;
    private Sort.Direction sorDirectionASC = Sort.Direction.ASC;
    private Sort.Direction sorDirectionDESC = Sort.Direction.DESC;
    private String sortByName = "name";
    private String sortByDescription = "description";
    private String sortByDueDate = "dueDate";
    private String sortByStatus = "status";
    private String sortByPriority = "priority";
}
