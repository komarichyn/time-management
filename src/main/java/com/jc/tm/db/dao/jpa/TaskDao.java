package com.jc.tm.db.dao.jpa;

import com.jc.tm.db.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TaskDao extends BaseDao<Task, Long> {

    Page<Task> findAll(Pageable pageable) ;
    @Query(value = "select * from Task task where task.name like %:search%", nativeQuery = true)
    Collection<Task> findByName(@Param("search") String search);
}
