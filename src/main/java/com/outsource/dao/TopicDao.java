package com.outsource.dao;

import com.outsource.model.TopicDO;
import org.apache.ibatis.annotations.Param;

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
}
