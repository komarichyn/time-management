package com.jc.tm;

import java.sql.Connection;
import java.util.Scanner;

import com.jc.tm.database.dao.Jdbc;
import com.jc.tm.database.dao.TaskDao;
import com.jc.tm.database.dao.TaskDaoImpl;
import com.jc.tm.database.entity.Task;

/**
 * this main class for launch a whole application
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");
        Connection connection = Jdbc.getConnection();
    }
}
