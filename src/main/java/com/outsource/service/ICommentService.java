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

    /**
     * 通过id查找留言
     * @param id
     * @return
     */
    CommentDO findComment(int id);

    /**
     * 审核留言
     * @param id
     * @param status
     * @param customerRemark
     * @return
     */
    Integer auditComment(int id, int status, String customerRemark);
}
