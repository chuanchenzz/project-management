package com.outsource.service;

import com.outsource.model.CommentDO;

/**
 * @author chuanchen
 */
public interface ICommentService {

    /**
     * 添加留言
     * @param commentDO
     * @return
     */
    CommentDO addComment(CommentDO commentDO);
}
