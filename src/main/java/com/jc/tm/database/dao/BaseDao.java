package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;

import java.util.List;

public interface BaseDao<E> {

    //create or insert
    void insert(E e);

    //update
    void update(E e);

    //get
    E getById(int id);

    //get all
    List<E> getAll();

    //delete
    void delete(E e);
}
