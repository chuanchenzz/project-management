package com.outsource.dao;

import com.outsource.model.CommentDO;

/**
 * @author chuanchen
 */
public interface CommentDao {
    /**
     * 添加留言
     * @param commentDO
     * @return
     */
    int addComment(CommentDO commentDO);
}
