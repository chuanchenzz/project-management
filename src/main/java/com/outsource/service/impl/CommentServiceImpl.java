package com.outsource.service.impl;

import com.outsource.dao.CommentDao;
import com.outsource.model.CommentDO;
import com.outsource.model.RedisKey;
import com.outsource.service.ICommentService;
import com.outsource.util.KeyUtil;
import com.outsource.util.RedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author chuanchen
 */
@Service
public class CommentServiceImpl implements ICommentService{
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    CommentDao commentDao;
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
        String commentIdListKey = KeyUtil.generateKey(RedisKey.PROJECT_COMMENT_ID_LIST,commentDO.getProjectId());
        if(status == CommentDO.DisplayStatusEnum.DISPLAY.code){
            redisOperation.addZSetItem(commentIdListKey,id,commentDO.getTime().getTime());
        }else if(status == CommentDO.DisplayStatusEnum.NOT_DISPLAY.code){
            redisOperation.removeZSetEntry(commentIdListKey,id);
        }
        return id;
    }
}
