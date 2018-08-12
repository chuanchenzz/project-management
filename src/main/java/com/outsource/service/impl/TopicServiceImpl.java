package com.outsource.service.impl;

import com.outsource.dao.TopicDao;
import com.outsource.dao.TopicTypeDao;
import com.outsource.model.RedisKey;
import com.outsource.model.TopicDO;
import com.outsource.model.TopicTypeDO;
import com.outsource.service.ITopicService;
import com.outsource.util.KeyUtil;
import com.outsource.util.RedisOperation;
import com.outsource.util.StringRedisOperation;
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
    @Autowired
    StringRedisOperation stringRedisOperation;
    private static final String ZERO = "0";


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
        if (topicTypeDO == null || topicTypeDO.getStatus().equals(TopicTypeDO.StatusEnum.HIDDEN.statusCode)) {
            return id;
        }
        String topicIdListKey = KeyUtil.generateKey(RedisKey.TOPIC_ID_LIST, id);
        if (redisOperation.hasKey(topicIdListKey)) {
            logger.info("the topic type has topic, id:{}", id);
            return null;
        }
        int deleteRow = topicTypeDao.updateTopicTypeStatus(id, TopicTypeDO.StatusEnum.HIDDEN.statusCode);
        if (deleteRow <= 0) {
            return null;
        }
        String topicTypeKey = KeyUtil.generateKey(RedisKey.TOPIC_TYPE, id);
        topicTypeDO.setStatus(TopicTypeDO.StatusEnum.HIDDEN.statusCode);
        redisOperation.set(topicTypeKey, topicTypeDO);
        return id;
    }

    @Override
    public TopicDO addTopic(TopicDO topicDO) {
        topicDO.setPubTime(new Date());
        topicDO.setDisplayStatus(TopicDO.StatusEnum.HIDDEN.statusCode);
        try {
            topicDao.addTopic(topicDO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String topicKey = KeyUtil.generateKey(RedisKey.TOPIC, topicDO.getId());
        redisOperation.set(topicKey, topicDO);
        // 更新所属文章类型下的文章id列表
        String topicIdListKey = KeyUtil.generateKey(RedisKey.TOPIC_ID_LIST, topicDO.getClassification());
        redisOperation.addZSetItem(topicIdListKey, topicDO.getId(), topicDO.getPubTime().getTime());
        // 初始化文章浏览数
        String scanCountKey = KeyUtil.generateKey(RedisKey.TOPIC_SCAN_COUNT, topicDO.getId());
        stringRedisOperation.set(scanCountKey, ZERO);
        return topicDO;
    }

    @Override
    public TopicDO findTopic(int id) {
        String topicKey = KeyUtil.generateKey(RedisKey.TOPIC, id);
        TopicDO topicDO = (TopicDO) redisOperation.get(topicKey);
        if (topicDO == null) {
            topicDO = topicDao.getTopicById(id);
            if (topicDO == null) {
                return null;
            }
            redisOperation.set(topicKey, topicDO);
        }
        return topicDO;
    }

    @Override
    public Integer auditTopic(int id, int status) {
        int auditRow = topicDao.updateTopicStatus(id, status);
        if (auditRow <= 0) {
            logger.info("topic may be not found! id:{}", id);
            return null;
        }
        TopicDO topicDO = findTopic(id);
        if (topicDO == null) {
            return null;
        }
        topicDO.setDisplayStatus(status);
        String topicKey = KeyUtil.generateKey(RedisKey.TOPIC, id);
        redisOperation.set(topicKey, topicDO);
        String topicIdListKey = KeyUtil.generateKey(RedisKey.TOPIC_ID_LIST, topicDO.getClassification());
        if (status == TopicDO.StatusEnum.DISPLAY.statusCode) {
            redisOperation.addZSetItem(topicIdListKey, id, topicDO.getPubTime().getTime());
        } else if (status == TopicDO.StatusEnum.HIDDEN.statusCode) {
            redisOperation.removeZSetEntry(topicIdListKey, id);
        }
        return id;
    }

    @Override
    public Integer updateTopic(TopicDO topicDO) {
        int topicId = topicDO.getId();
        TopicDO topic = findTopic(topicId);
        if(topic == null){
            return null;
        }
        // 更新文章所属分类
        if(topicDO.getClassification() != null && !topicDO.getClassification().equals(topic.getClassification())){
            TopicTypeDO topicType = findTopicType(topicDO.getClassification());
            if(topicType != null && topicType.getStatus().equals(TopicTypeDO.StatusEnum.DISPLAY.statusCode)) {
                String oldTopicIdListKey = KeyUtil.generateKey(RedisKey.TOPIC_ID_LIST, topic.getClassification());
                redisOperation.removeZSetEntry(oldTopicIdListKey, topicId);
                String newTopicIdListKey = KeyUtil.generateKey(RedisKey.TOPIC_ID_LIST,topicDO.getClassification());
                redisOperation.addZSetItem(newTopicIdListKey,topicId,topic.getPubTime().getTime());
            }else {
                logger.warn("topic type has be delete! topicTypeId:{}",topicDO.getClassification());
                return null;
            }
        }
        int updateRow = topicDao.updateTopic(topicDO);
        if(updateRow <= 0){
            logger.warn("update fail! maybe not found! id:{}",topicId);
            return null;
        }
        topic.update(topicDO);
        String topicKey = KeyUtil.generateKey(RedisKey.TOPIC,topicId);
        redisOperation.set(topicKey,topic);
        return topicId;
    }
}