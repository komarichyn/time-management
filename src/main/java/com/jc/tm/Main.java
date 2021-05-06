package com.jc.tm;

import java.util.Scanner;

import com.jc.tm.database.dao.Jdbc;
import com.jc.tm.database.dao.TaskDao;
import com.jc.tm.database.dao.TaskDaoImpl;
import com.jc.tm.database.entity.Comment;
import com.jc.tm.helper.LoadPropertiesHelper;

/**
 * this main class for launch a whole application
 */
public class Main {
    public static void main(String[] args) {
        Jdbc.getConnection();
    }
}
