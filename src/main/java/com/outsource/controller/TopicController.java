package com.outsource.controller;

import com.outsource.constant.StatusCodeEnum;
import com.outsource.model.*;
import com.outsource.service.ITopicService;
import com.outsource.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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

    @RequestMapping(value = "/type/list",method = RequestMethod.GET)
    public JsonResponse<List<TopicTypeVO>> findTopicTypeList(){
        List<TopicTypeVO> topicTypeList = topicService.findTopicTypeList();
        return new JsonResponse<>(topicTypeList,StatusCodeEnum.SUCCESS.getCode());
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
        return deleteResult == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "文章类型不存在或该类型下还有文章!") : new JsonResponse<>(deleteResult, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponse<TopicVO> addTopic(@RequestParam("title") String title, @RequestParam("author_name") String authorName, @RequestParam("content") String content, @RequestParam("classification") int classification) {
        if (StringUtils.isEmpty(title, authorName, content) || classification <= 0) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        TopicDO topicDO = new TopicDO(title, authorName, content, classification);
        TopicTypeDO topicTypeDO = topicService.findTopicType(classification);
        if (topicTypeDO == null || topicTypeDO.getStatus().equals(TopicTypeDO.StatusEnum.HIDDEN.statusCode)) {
            return new JsonResponse<>(StatusCodeEnum.NOT_FOUND.getCode(), "文章类型未找到!");
        }
        topicDO = topicService.addTopic(topicDO);
        return topicDO == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内存错误!") : new JsonResponse<>(new TopicVO(topicDO), StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/pages",method = RequestMethod.GET)
    public JsonResponse<Pages<TopicVO>> findTopicList(@RequestParam("topic_type_id") int topicTypeId,@RequestParam("page_number") int pageNumber,@RequestParam("page_size") int pageSize){
        if(topicTypeId <= 0 || pageNumber <= 0 || pageSize <= 0){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        Integer totalCount = topicService.findTopicCount(topicTypeId);
        if(totalCount == null){
            return new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(),"内部错误!");
        }
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if((pageNumber - 1) * pageSize >= totalCount){
            return new JsonResponse<>(new Pages<>(totalPage,totalCount, Collections.emptyList()),StatusCodeEnum.SUCCESS.getCode());
        }
        List<TopicVO> topicList = topicService.findTopicList(topicTypeId,pageNumber,pageSize);
        return new JsonResponse<>(new Pages<>(totalPage,totalCount,topicList),StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/audit/{id}",method = RequestMethod.POST)
    public JsonResponse<Integer> auditTopic(@PathVariable("id") int id, @RequestParam("status") int status){
        boolean invalidArgs = id <= 0 || (status != TopicDO.StatusEnum.HIDDEN.statusCode && status != TopicDO.StatusEnum.DISPLAY.statusCode);
        if(invalidArgs){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        TopicDO topicDO = topicService.findTopic(id);
        if(topicDO == null || topicDO.getDisplayStatus() == status){
            return new JsonResponse<>(StatusCodeEnum.NOT_FOUND.getCode(),"操作错误!");
        }
        Integer updateResult = topicService.auditTopic(id,status);
        return updateResult == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(),"内部错误!") : new JsonResponse<>(updateResult,StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    public JsonResponse<Integer> updateTopic(@PathVariable("id") int id, @RequestParam(value = "title",required = false) String title,@RequestParam(value = "author_name",required = false) String authorName,
                                             @RequestParam(value = "classification",required = false) int classification,@RequestParam(value = "content",required = false) String content){
        if (StringUtils.isAllEmpty(title, authorName, content) || classification <= 0 || id <= 0) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        TopicDO topicDO = topicService.findTopic(id);
        if(topicDO == null){
            return new JsonResponse<>(StatusCodeEnum.NOT_FOUND.getCode(),"文章未找到!");
        }
        if(StringUtils.isNotEmpty(title)) {
            topicDO.setTitle(title);
        }
        if(StringUtils.isNotEmpty(authorName)) {
            topicDO.setAuthorName(authorName);
        }
        topicDO.setClassification(classification);
        if(StringUtils.isNotEmpty(content)) {
            topicDO.setContent(content);
        }
        Integer updateResult = topicService.updateTopic(topicDO);
        return updateResult == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(),"内部错误!") : new JsonResponse<>(updateResult,StatusCodeEnum.SUCCESS.getCode());
    }
}
