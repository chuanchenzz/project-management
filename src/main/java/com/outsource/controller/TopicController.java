package com.outsource.controller;

import com.outsource.constant.StatusCodeEnum;
import com.outsource.model.*;
import com.outsource.service.ITopicService;
import com.outsource.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chuanchen
 */
@RestController
@RequestMapping(value = "/topic")
public class TopicController {
    @Autowired
    ITopicService topicService;

    @RequestMapping(value = "/type", method = RequestMethod.POST)
    public JsonResponse<TopicTypeVO> addTopicType(@RequestParam("name") String name) {
        if (StringUtils.isEmpty(name)) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        TopicTypeDO topicTypeDO = topicService.findTopicType(name);
        if (topicTypeDO != null) {
            return new JsonResponse<>(StatusCodeEnum.REPEAT_ADD.getCode(), "重复添加!");
        }
        topicTypeDO = topicService.addTopicType(name);
        return topicTypeDO == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!") : new JsonResponse<>(new TopicTypeVO(topicTypeDO), StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/type/{id}", method = RequestMethod.POST)
    public JsonResponse<Integer> updateTopicType(@PathVariable("id") int id, @RequestParam("name") String name) {
        if (id <= 0 || StringUtils.isEmpty(name)) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        Integer updateResult = topicService.updateTopicType(id, name);
        return updateResult == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!") : new JsonResponse<>(updateResult, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/type/delete/{id}", method = RequestMethod.POST)
    public JsonResponse<Integer> deleteTopicType(@PathVariable("id") int id) {
        if (id <= 0) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        Integer deleteResult = topicService.deleteTopicType(id);
        return deleteResult == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!") : new JsonResponse<>(deleteResult, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponse<TopicVO> addTopic(@RequestParam("title") String title, @RequestParam("author_name") String authorName, @RequestParam("content") String content, @RequestParam("classification") int classification) {
        if (StringUtils.isEmpty(title, authorName, content) || classification <= 0) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        TopicDO topicDO = new TopicDO(title, authorName, content, classification);
        TopicTypeDO topicTypeDO = topicService.findTopicType(classification);
        if (topicTypeDO == null || topicTypeDO.getStatus().equals(TopicTypeDO.StatusEnum.HIDDEN)) {
            return new JsonResponse<>(StatusCodeEnum.NOT_FOUND.getCode(), "文章类型未找到!");
        }
        topicDO = topicService.addTopic(topicDO);
        return topicDO == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内存错误!") : new JsonResponse<>(new TopicVO(topicDO), StatusCodeEnum.SUCCESS.getCode());
    }
}
