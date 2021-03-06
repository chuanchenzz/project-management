package com.outsource.dao;

import com.outsource.model.CommentDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据id查找评论
     * @param id
     * @return
     */
    CommentDO getCommentById(@Param("id") int id);

    /**
     * 审核留言
     * @param id
     * @param status
     * @param customerRemark
     * @return
     */
    int auditComment(@Param("id") int id, @Param("status") int status, @Param("customer_remark") String customerRemark);

    /**
     * 获取特定项目下的留言
     * @param projectId
     * @return
     */
    List<CommentDO> listCommentByProjectId(@Param("projectId") int projectId);

    /**
     * 获取所有留言
     * @return
     */
    List<CommentDO> listComment();
}
