package com.jc.tm.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto {
    public static PaginationDto DEFAULT = new PaginationDto(0, 10, 1);
    private int index;
    private int size;
    private int page;
}
