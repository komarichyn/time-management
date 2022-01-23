package com.jc.tm.controller;

public class SortnSearch {
    private String searchBy;
    private String sortBy;
    public SortnSearch(String searchBy, String sortBy) {
        this.searchBy = searchBy;
        this.sortBy = sortBy;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public String getSortBy() {
        return sortBy;
    }
}
