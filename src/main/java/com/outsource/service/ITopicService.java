package com.outsource.service;

import com.outsource.model.TopicDO;
import com.outsource.model.TopicTypeDO;

/**
 * @author chuanchen
 */
public interface ITopicService {

    /**
     * 通过name查找文章类型
     * @param name
     * @return
     */
    TopicTypeDO findTopicType(String name);

    /**
     * 添加文章类型
     * @param name
     * @return
     */
    TopicTypeDO addTopicType(String name);

    /**
     * 更新文章类型
     * @param id
     * @param name
     * @return
     */
    Integer updateTopicType(int id, String name);

    /**
     * 根据id查找文章类型
     * @param id
     * @return
     */
    TopicTypeDO findTopicType(int id);

    /**
     * 删除文章类型
     * @param id
     * @return
     */
    Integer deleteTopicType(int id);

    /**
     * 添加文章
     * @param topicDO
     * @return
     */
    TopicDO addTopic(TopicDO topicDO);

    /**
     * 查找文章
     * @param id
     * @return
     */
    TopicDO findTopic(int id);

    /**
     * 审核文章
     * @param id
     * @param status
     * @return
     */
    Integer auditTopic(int id, int status);

    /**
     * 更新文章信息
     * @param topicDO
     * @return
     */
    Integer updateTopic(TopicDO topicDO);
}
