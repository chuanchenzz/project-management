package com.outsource.service.impl;

import com.outsource.dao.TopicDao;
import com.outsource.dao.TopicTypeDao;
import com.outsource.model.*;
import com.outsource.service.ITopicService;
import com.outsource.util.KeyUtil;
import com.outsource.util.RedisOperation;
import com.outsource.util.StringRedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        TopicTypeDO topicTypeDO = new TopicTypeDO(name, new Date(), TopicTypeDO.StatusEnum.DISPLAY.statusCode);
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
        redisOperation.addZSetItem(RedisKey.TOPIC_TYPE_ID_LIST,topicTypeDO.getId(),topicTypeDO.getTime().getTime());
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
        redisOperation.removeZSetEntry(RedisKey.TOPIC_TYPE_ID_LIST,id);
        return id;
    }

    @Override
    public TopicDO addTopic(TopicDO topicDO) {
        topicDO.setPubTime(new Date());
        topicDO.setDisplayStatus(TopicDO.StatusEnum.DISPLAY.statusCode);
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
        return id;
    }

    @Override
    public Integer deleteTopic(int id) {
        TopicDO topicDO = findTopic(id);
        if(topicDO == null){
            return null;
        }
        int updateRow = topicDao.updateTopicStatus(id,TopicDO.StatusEnum.DELETE.statusCode);
        if(updateRow <= 0){
            return null;
        }
        topicDO.setDisplayStatus(TopicDO.StatusEnum.DELETE.statusCode);
        String topicKey = KeyUtil.generateKey(RedisKey.TOPIC,id);
        redisOperation.set(topicKey,topicDO);
        String topicIdListKey = KeyUtil.generateKey(RedisKey.TOPIC_ID_LIST,topicDO.getClassification());
        redisOperation.removeZSetEntry(topicIdListKey,topicDO.getId());
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

    @Override
    public List<Integer> findTopicTypeIdList() {
        List<Integer> topicTypeIdList = redisOperation.rangeZSet(RedisKey.TOPIC_TYPE_ID_LIST,1,Integer.valueOf(String.valueOf(redisOperation.zSetSize(RedisKey.TOPIC_TYPE_ID_LIST))));
        if(CollectionUtils.isEmpty(topicTypeIdList)){
            List<TopicTypeDO> topicTypeList = topicTypeDao.listTopicType();
            if(CollectionUtils.isEmpty(topicTypeList)){
                return null;
            }
            for (TopicTypeDO topicTypeDO : topicTypeList){
                redisOperation.addZSetItem(RedisKey.TOPIC_TYPE_ID_LIST,topicTypeDO.getId(),topicTypeDO.getTime().getTime());
            }
            topicTypeIdList = redisOperation.rangeZSet(RedisKey.TOPIC_TYPE_ID_LIST,1,Integer.valueOf(String.valueOf(redisOperation.zSetSize(RedisKey.TOPIC_TYPE_ID_LIST))));
        }
        return topicTypeIdList;
    }

    @Override
    public List<TopicTypeVO> findTopicTypeList() {
        List<Integer> topicTypeIdList = findTopicTypeIdList();
        if(topicTypeIdList == null){
            return Collections.emptyList();
        }
        List<TopicTypeVO> topicTypeList = new ArrayList<>(topicTypeIdList.size());
        for (Integer topicTypeId : topicTypeIdList){
            TopicTypeDO topicTypeDO = findTopicType(topicTypeId);
            if(topicTypeDO != null){
                TopicTypeVO topicTypeVO = new TopicTypeVO(topicTypeDO);
                topicTypeVO.setTopicCount(findTopicCount(topicTypeDO.getId()));
                topicTypeList.add(topicTypeVO);
            }
        }
        return topicTypeList;
    }

    @Override
    public Integer findTopicCount(int topicTypeId) {
        String topicIdListKey = KeyUtil.generateKey(RedisKey.TOPIC_ID_LIST,topicTypeId);
        Long size;
        try {
            size = redisOperation.zSetSize(topicIdListKey);
        } catch (Exception e) {
            logger.error(String.format("redisOperation.zSetSize(%s) error!",topicIdListKey),e);
            return null;
        }
        return Integer.valueOf(String.valueOf(size));
    }

    @Override
    public List<Integer> findTopicIdList(int topicTypeId, int pageNumber, int pageSize) {
        String topicIdListKey = KeyUtil.generateKey(RedisKey.TOPIC_ID_LIST,topicTypeId);
        List<Integer> topicIdList = redisOperation.rangeZSet(topicIdListKey,pageNumber,pageSize);
        if(CollectionUtils.isEmpty(topicIdList)){
            List<TopicDO> topicList = topicDao.listProjectByTypeId(topicTypeId);
            if(CollectionUtils.isEmpty(topicList)){
                return Collections.emptyList();
            }
            for (TopicDO topic : topicList){
                redisOperation.addZSetItem(topicIdListKey,topic.getId(),topic.getPubTime().getTime());
            }
        }
        return redisOperation.rangeZSet(topicIdListKey,pageNumber,pageSize);
    }

    @Override
    public List<TopicVO> findTopicList(int topicTypeId, int pageNumber, int pageSize) {
        List<Integer> topicIdList = findTopicIdList(topicTypeId,pageNumber,pageSize);
        if(CollectionUtils.isEmpty(topicIdList)){
            return Collections.emptyList();
        }
        List<TopicVO> topicList = new ArrayList<>(topicIdList.size());
        for (Integer topicId : topicIdList){
            TopicDO topic = findTopic(topicId);
            if(topic != null){
                topicList.add(new TopicVO(topic));
            }
        }
        return topicList;
    }

    @Override
    public Integer incrementScanCount(int topicId) {
        String topicScanCountKey = KeyUtil.generateKey(RedisKey.TOPIC_SCAN_COUNT,topicId);
        try {
            return stringRedisOperation.increment(topicScanCountKey);
        } catch (Exception e) {
            logger.error(String.format("increment operation fail! topicId:%d",topicId),e);
            return null;
        }
    }
}
