package com.jc.tm.db.dao.jpa;

import com.jc.tm.db.entity.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends BaseDao<Project, Long> {

}
