package com.jc.tm;

import com.jc.tm.database.dao.Jdbc;

/**
 * this main class for launch a whole application
 */
public class Main {
    public static void main(String[] args) {
        Jdbc.getConnection();
    }
}
