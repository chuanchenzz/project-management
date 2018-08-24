package com.outsource.dao;

import com.outsource.model.TopicDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chuanchen
 */
public interface TopicDao {
    /**
     * 添加文章
     * @param topicDO
     * @return
     */
    int addTopic(TopicDO topicDO);

    /**
     * 通过id查找文章
     * @param id
     * @return
     */
    TopicDO getTopicById(@Param("id") int id);

    /**
     * 更新文章显示状态
     * @param id
     * @param status
     * @return
     */
    int updateTopicStatus(@Param("id") int id, @Param("status") int status);

    /**
     * 更新文章信息
     * @param topicDO
     * @return
     */
    int updateTopic(TopicDO topicDO);

    /**
     * 获取具体文章类型下的文章
     * @param topicTypeId
     * @return
     */
    List<TopicDO> listTopicByTypeId(@Param("topicTypeId") int topicTypeId);

    /**
     * 获取所有的文章
     * @return
     */
    List<TopicDO> listTopic();
}
