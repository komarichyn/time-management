package com.jc.tm.db.dao.jpa;

import com.jc.tm.db.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends BaseDao<Comment, Long> {

}
