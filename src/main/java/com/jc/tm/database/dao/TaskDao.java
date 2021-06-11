package com.jc.tm.database.dao;

import com.jc.tm.database.entity.Task;
import com.jc.tm.service.PaginationDto;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao extends BaseDao<Task> {

    List<Task> getFiveDueDateTasks(PaginationDto paginationDto) throws SQLException;

}
