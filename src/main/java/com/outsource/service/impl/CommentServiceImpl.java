package com.outsource.service.impl;

import com.outsource.dao.CommentDao;
import com.outsource.model.CommentDO;
import com.outsource.service.ICommentService;
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
        return null;
    }
}
