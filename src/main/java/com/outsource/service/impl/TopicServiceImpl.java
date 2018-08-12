package com.outsource.service.impl;

import com.outsource.dao.TopicDao;
import com.outsource.dao.TopicTypeDao;
import com.outsource.model.RedisKey;
import com.outsource.model.TopicDO;
import com.outsource.model.TopicTypeDO;
import com.outsource.service.ITopicService;
import com.outsource.util.KeyUtil;
import com.outsource.util.RedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author chuanchen
 */
@Service
public class TopicServiceImpl implements ITopicService {
    private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);
    @Autowired
    TopicDao topicDao;
    @Autowired
    TopicTypeDao topicTypeDao;
    @Autowired
    RedisOperation redisOperation;


    @Override
    public TopicTypeDO findTopicType(String name) {
        try {
            return topicTypeDao.getTopicTypeByName(name);
        } catch (Exception e) {
            logger.error(String.format("find topic type error! name:%s", name));
            return null;
        }
    }

    @Override
    public TopicTypeDO addTopicType(String name) {
        TopicTypeDO topicTypeDO = new TopicTypeDO(name, new Date(), TopicTypeDO.StatusEnum.HIDDEN.statusCode);
        try {
            topicTypeDao.addTopicType(topicTypeDO);
        } catch (DuplicateKeyException e) {
            return findTopicType(name);
        } catch (Exception e) {
            logger.error(String.format("add topic type error! name:%s", name), e);
            return null;
        }
        String topicTypeKey = KeyUtil.generateKey(RedisKey.TOPIC_TYPE, topicTypeDO.getId());
        redisOperation.set(topicTypeKey, topicTypeDO);
        return topicTypeDO;
    }

    @Override
    public Integer updateTopicType(int id, String name) {
        int updateRow = topicTypeDao.updateTopicTypeName(id, name);
        if (updateRow <= 0) {
            logger.warn("update fail! maybe topic type not found! id:{}", id);
            return null;
        }
        String topicTypeKey = KeyUtil.generateKey(RedisKey.TOPIC_TYPE, id);
        TopicTypeDO topicTypeDO = findTopicType(id);
        if (topicTypeDO == null) {
            return null;
        }
        topicTypeDO.setName(name);
        redisOperation.set(topicTypeKey, name);
        return id;
    }

    @Override
    public TopicTypeDO findTopicType(int id) {
        String topicTypeKey = KeyUtil.generateKey(RedisKey.TOPIC_TYPE, id);
        TopicTypeDO topicTypeDO = (TopicTypeDO) redisOperation.get(topicTypeKey);
        if (topicTypeDO == null) {
            topicTypeDO = topicTypeDao.getTopicTypeById(id);
            if (topicTypeDO == null) {
                return null;
            }
            redisOperation.set(topicTypeKey, topicTypeDO);
        }
        return topicTypeDO;
    }

    @Override
    public Integer deleteTopicType(int id) {
        TopicTypeDO topicTypeDO = findTopicType(id);
        if(topicTypeDO == null || topicTypeDO.getStatus().equals(TopicTypeDO.StatusEnum.HIDDEN.statusCode)){
            return id;
        }
        String topicIdListKey = KeyUtil.generateKey(RedisKey.TOPIC_ID_LIST,id);
        if(redisOperation.hasKey(topicIdListKey)){
            logger.info("the topic type has topic, id:{}",id);
            return null;
        }
        int deleteRow = topicTypeDao.updateTopicTypeStatus(id, TopicTypeDO.StatusEnum.HIDDEN.statusCode);
        if(deleteRow <= 0){
            return null;
        }
        String topicTypeKey = KeyUtil.generateKey(RedisKey.TOPIC_TYPE,id);
        topicTypeDO.setStatus(TopicTypeDO.StatusEnum.HIDDEN.statusCode);
        redisOperation.set(topicTypeKey,topicTypeDO);
        return id;
    }

    @Override
    public TopicDO addTopic(TopicDO topicDO) {
        topicDO.setPubTime(new Date());
        topicDO.setDisplayStatus(TopicDO.StatusEnum.HIDDEN.statusCode);

        return null;
    }
}
