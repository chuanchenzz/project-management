package com.outsource.service;

import com.outsource.model.TopicDO;
import com.outsource.model.TopicTypeDO;
import com.outsource.model.TopicTypeVO;
import com.outsource.model.TopicVO;

import java.util.List;

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
     * 删除文章
     * @param id
     * @return
     */
    Integer deleteTopic(int id);

    /**
     * 更新文章信息
     * @param topicDO
     * @return
     */
    Integer updateTopic(TopicDO topicDO);

    /**
     * 获取文章分类id列表
     * @return
     */
    List<Integer> findTopicTypeIdList();

    /**
     * 获取文章分类列表
     * @return
     */
    List<TopicTypeVO> findTopicTypeList();

    /**
     * 查找具体文章类型下的文章数
     * @param topicTypeId
     * @return
     */
    Integer findTopicCount(int topicTypeId);

    /**
     * 获取文章id列表
     * @param topicTypeId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Integer> findTopicIdList(int topicTypeId, int pageNumber, int pageSize);

    /**
     * 获取文章列表
     * @param topicTypeId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TopicVO> findTopicList(int topicTypeId, int pageNumber, int pageSize);

    /**
     * 文章浏览数加一
     * @param topicId
     * @return
     */
    Integer incrementScanCount(int topicId);

    /**
     * 查找文章浏览数
     * @param topicId
     * @return
     */
    Integer findTopicScanCount(int topicId);
}
