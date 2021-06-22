package com.jc.tm.db.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.sql.SQLException;
import java.util.List;

@NoRepositoryBean
public interface BaseDao<T, ID> extends JpaRepository<T, ID> {

}
