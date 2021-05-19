package com.jc.tm.database.dao;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<E> {

    //create or insert
    E insert(E e) throws SQLException;

    //update
    void update(E e) throws SQLException;

    //get
    E getById(Long id) throws SQLException;

    //get all
    List<E> getAll() throws SQLException;

    //delete
    boolean delete(E e) throws SQLException;
}
