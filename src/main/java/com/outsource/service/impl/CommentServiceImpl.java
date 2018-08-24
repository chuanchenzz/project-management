package com.outsource.service.impl;

import com.outsource.dao.CommentDao;
import com.outsource.model.CommentDO;
import com.outsource.model.CommentVO;
import com.outsource.model.ProjectDO;
import com.outsource.model.RedisKey;
import com.outsource.service.ICommentService;
import com.outsource.service.IProjectService;
import com.outsource.util.KeyUtil;
import com.outsource.util.RedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author chuanchen
 */
@Service
public class CommentServiceImpl implements ICommentService{
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    CommentDao commentDao;
    @Autowired
    IProjectService projectService;
    @Autowired
    RedisOperation redisOperation;
    @Override
    public CommentDO addComment(CommentDO commentDO) {
        commentDO.setStatus(CommentDO.DisplayStatusEnum.NOT_DISPLAY.code);
        commentDO.setTime(new Date());
        try {
            commentDao.addComment(commentDO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String commentKey = KeyUtil.generateKey(RedisKey.COMMENT,commentDO.getId());
        redisOperation.set(commentKey,commentDO);
        String commentIdListKey = KeyUtil.generateKey(RedisKey.PROJECT_COMMENT_ID_LIST,commentDO.getProjectId());
        redisOperation.addZSetItem(commentIdListKey,commentDO.getId(),commentDO.getTime().getTime());
        redisOperation.addZSetItem(RedisKey.COMMENT_ID_LIST,commentDO.getId(),commentDO.getTime().getTime());
        return commentDO;
    }

    @Override
    public CommentDO findComment(int id) {
        String commentKey = KeyUtil.generateKey(RedisKey.COMMENT,id);
        CommentDO commentDO = (CommentDO) redisOperation.get(commentKey);
        if(commentDO == null){
            commentDO = commentDao.getCommentById(id);
            if(commentDO == null){
                return null;
            }
            redisOperation.set(commentKey,commentDO);
        }
        return commentDO;
    }

    @Override
    public Integer auditComment(int id, int status, String customerRemark) {
        CommentDO commentDO = findComment(id);
        if(commentDO == null){
            return null;
        }
        int updateRow = commentDao.auditComment(id,status,customerRemark);
        if(updateRow <= 0){
            logger.warn("audit comment fail, maybe comment not found! id:{}",id);
            return null;
        }
        commentDO.setStatus(status);
        commentDO.setCustomerRemark(customerRemark);
        String commentKey = KeyUtil.generateKey(RedisKey.COMMENT,id);
        redisOperation.set(commentKey,commentDO);
        return id;
    }

    @Override
    public Integer findCommentCountByProjectId(int projectId) {
        String commentIdListKey = KeyUtil.generateKey(RedisKey.PROJECT_COMMENT_ID_LIST,projectId);
        if(redisOperation.hasKey(commentIdListKey)){
            return Integer.valueOf(String.valueOf(redisOperation.zSetSize(commentIdListKey)));
        }
        List<CommentDO> commentList = commentDao.listCommentByProjectId(projectId);
        if(CollectionUtils.isEmpty(commentList)){
            return 0;
        }
        for (CommentDO comment : commentList){
            redisOperation.addZSetItem(commentIdListKey,comment.getId(),comment.getTime().getTime());
        }
        return commentList.size();
    }

    @Override
    public List<Integer> findCommentIdList(int projectId, int pageNumber, int pageSize) {
        String commentIdListKey = KeyUtil.generateKey(RedisKey.PROJECT_COMMENT_ID_LIST,projectId);
        List<Integer> commentIdList = redisOperation.rangeZSet(commentIdListKey,pageNumber,pageSize);
        if(CollectionUtils.isEmpty(commentIdList)){
            List<CommentDO> commentList = commentDao.listCommentByProjectId(projectId);
            if(CollectionUtils.isEmpty(commentList)){
                return Collections.emptyList();
            }
            for (CommentDO comment : commentList){
                redisOperation.addZSetItem(commentIdListKey,comment.getId(),comment.getTime().getTime());
            }
            commentIdList = redisOperation.rangeZSet(commentIdListKey,pageNumber,pageSize);
        }
        return commentIdList;
    }

    @Override
    public List<CommentDO> findCommentList(int projectId, int pageNumber, int pageSize) {
        List<Integer> commentIdList = findCommentIdList(projectId,pageNumber,pageSize);
        List<CommentDO> commentList = new ArrayList<>(commentIdList.size());
        for (Integer commentId : commentIdList){
            CommentDO comment = findComment(commentId);
            if(comment != null){
                commentList.add(comment);
            }
        }
        return commentList;
    }

    @Override
    public Integer findCommentCount() {
        if(redisOperation.hasKey(RedisKey.COMMENT_ID_LIST)){
            return Integer.valueOf(String.valueOf(redisOperation.zSetSize(RedisKey.COMMENT_ID_LIST)));
        }
        List<CommentDO> commentList = commentDao.listComment();
        if(CollectionUtils.isEmpty(commentList)){
            return 0;
        }
        for (CommentDO comment : commentList){
            redisOperation.addZSetItem(RedisKey.COMMENT_ID_LIST,comment.getId(),comment.getTime().getTime());
        }
        return commentList.size();
    }

    @Override
    public List<Integer> findCommentIdList(int pageNumber, int pageSize) {
        Integer commentCount = findCommentCount();
        if(commentCount == 0){
            return Collections.emptyList();
        }
        List<Integer> commentIdList = redisOperation.rangeZSet(RedisKey.COMMENT_ID_LIST,pageNumber,pageSize);
        if(CollectionUtils.isEmpty(commentIdList)){
            return Collections.emptyList();
        }
        return commentIdList;
    }

    @Override
    public List<CommentVO> findCommentList(int pageNumber, int pageSize) {
        List<Integer> commentIdList = findCommentIdList(pageNumber,pageSize);
        List<CommentVO> commentList = new ArrayList<>(commentIdList.size());
        for(Integer commentId : commentIdList){
            CommentDO comment = findComment(commentId);
            if(comment != null){
                CommentVO commentVO = new CommentVO(comment);
                ProjectDO project = projectService.findProject(comment.getProjectId());
                if(project != null){
                    commentVO.setProjectName(project.getName());
                }
                commentList.add(commentVO);
            }
        }
        return commentList;
    }
}
