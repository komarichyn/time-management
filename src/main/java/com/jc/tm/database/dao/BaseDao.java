package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<E> {

    //create or insert
    void insert(E e) throws SQLException;

    //update
    void update(E e) throws SQLException;

    //get
    E getById(int id) throws SQLException;

    //get all
    List<E> getAll();

    //delete
    boolean delete(E e) throws SQLException;
}
