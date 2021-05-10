package com.jc.tm.service;

import lombok.Data;

@Data
public class PaginationDto {
    private int index;
    private int size;
    private int page;
}
