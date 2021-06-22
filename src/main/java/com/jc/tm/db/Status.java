package com.jc.tm.db;

/**
 * status of for task
 */
public enum Status {
    TODO("TODO"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETE("COMPLETE"),
    PAUSE("PAUSE");

    private String fieldName;
    Status(String fieldName) {
        this.fieldName = fieldName;
    }

    public String toString() {
        return fieldName;
    }
}

