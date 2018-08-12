package com.outsource.dao;

import com.outsource.model.TopicTypeDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author chuanchen
 */
public interface TopicTypeDao {

    /**
     * 通过name查找topicType
     * @param name
     * @return
     */
    TopicTypeDO getTopicTypeByName(@Param("name") String name);

    /**
     * 通过id查找topicType
     * @param id
     * @return
     */
    TopicTypeDO getTopicTypeById(@Param("id") int id);

    /**
     * 添加文章类型
     * @param topicTypeDO
     * @return
     */
    int addTopicType(TopicTypeDO topicTypeDO);

    /**
     * 更新文章类型
     * @param id
     * @param name
     * @return
     */
    int updateTopicTypeName(@Param("id") int id, @Param("name") String name);

    /**
     * 更新文章类型status属性
     * @param id
     * @param status
     * @return
     */
    int updateTopicTypeStatus(@PathVariable("id") int id, @Param("status") int status);
}
