package com.outsource.service;

import com.outsource.model.CommentDO;

import java.util.List;

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

    /**
     * 查找留言数通过项目id
     * @param projectId
     * @return
     */
    Integer findCommentCountByProjectId(int projectId);

    /**
     * 分页查找留言id
     * @param projectId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Integer> findCommentIdList(int projectId, int pageNumber, int pageSize);

    /**
     * 分页查找留言
     * @param projectId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<CommentDO> findCommentList(int projectId, int pageNumber, int pageSize);

    /**
     * 查找留言总数
     * @return
     */
    Integer findCommentCount();

    /**
     * 分页查找留言id列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Integer> findCommentIdList(int pageNumber, int pageSize);

    /**
     * 分页查找留言列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<CommentDO> findCommentList(int pageNumber, int pageSize);
}
