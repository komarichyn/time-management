package com.jc.tm.db.dao.jpa;

import com.jc.tm.db.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDao extends BaseDao<Task, Long> {

    Page<Task> findAll(Pageable pageable) ;

}
