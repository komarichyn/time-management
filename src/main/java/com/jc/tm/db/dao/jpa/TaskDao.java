package com.jc.tm.db.dao.jpa;

import com.jc.tm.db.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDao extends BaseDao<Task, Long> {

    Page<Task> findAll(Pageable pageable);
    @Query(value = "select * from task where task.name like %:search%", nativeQuery = true)
    Page<Task> findAllBy(Pageable pageable, @Param("search") String search);
}
