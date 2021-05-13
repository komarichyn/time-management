package com.jc.tm.database.dao;


import java.util.List;

public interface BaseDao<E> {

    //create or insert
    Long insert(E e);

    //update
    void update(E e);

    //get
    E getById(int id);

    //get all
    List<E> getAll();

    //delete
    boolean delete(E e);
}
