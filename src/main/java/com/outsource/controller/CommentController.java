package com.outsource.controller;

import com.outsource.constant.StatusCodeEnum;
import com.outsource.model.*;
import com.outsource.service.ICommentService;
import com.outsource.service.IProjectService;
import com.outsource.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author chuanchen
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    ICommentService commentService;
    @Autowired
    IProjectService projectService;
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public JsonResponse<Integer> addComment(@RequestParam("name")String name, @RequestParam("mobile") String mobile,@RequestParam(value = "email",required = false) String email,
                                            @RequestParam("project_id") int projectId,@RequestParam("content") String content){
        if(StringUtils.isEmpty(name,mobile,content) || projectId <= 0){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        ProjectDO projectDO = projectService.findProject(projectId);
        if(projectDO == null){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        CommentDO commentDO = new CommentDO();
        commentDO.setName(name);
        commentDO.setMobile(mobile);
        if(StringUtils.isNotEmpty(email)) {
            commentDO.setEmail(email);
        }
        commentDO.setProjectId(projectId);
        commentDO.setContent(content);
        commentDO = commentService.addComment(commentDO);
        return commentDO == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(),"内部错误!") : new JsonResponse<>(commentDO.getId(),StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/audit/{id}",method = RequestMethod.POST)
    public JsonResponse<Integer> auditComment(@PathVariable("id") int id,@RequestParam("customer_remark") String customerRemark,@RequestParam("status") int status){
        if(StringUtils.isEmpty(customerRemark) || id <= 0 || (status != CommentDO.DisplayStatusEnum.NOT_DISPLAY.code && status != CommentDO.DisplayStatusEnum.DISPLAY.code)){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        CommentDO commentDO = commentService.findComment(id);
        if(commentDO == null){
            return new JsonResponse<>(StatusCodeEnum.NOT_FOUND.getCode(),"评论未找到!");
        }
        Integer auditResult = commentService.auditComment(id,status,customerRemark);
        return auditResult == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(),"内部错误!") : new JsonResponse<>(auditResult,StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/{project_id}/pages",method = RequestMethod.GET)
    public JsonResponse<Pages<CommentDO>> findCommentListByProjectId(@PathVariable("project_id") int projectId,@RequestParam("page_number") int pageNumber, @RequestParam("page_size") int pageSize){
        if(projectId <= 0 || pageNumber <= 0 || pageSize <= 0){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        Integer commentCount = commentService.findCommentCountByProjectId(projectId);
        if((pageNumber - 1) * pageSize >= commentCount){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        int totalNumber = commentCount % pageSize == 0 ? commentCount / pageSize : commentCount / pageSize + 1;
        List<CommentDO> commentList = commentService.findCommentList(projectId,pageNumber,pageSize);
        return new JsonResponse<>(new Pages<>(totalNumber,commentCount,commentList),StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/pages",method = RequestMethod.GET)
    public JsonResponse<Pages<CommentVO>> findCommentList(@RequestParam("page_number") int pageNumber, @RequestParam("page_size") int pageSize){
        if(pageNumber <= 0 || pageSize <= 0){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        Integer commentCount = commentService.findCommentCount();
        int totalNumber = commentCount % pageSize == 0 ? commentCount / pageSize : commentCount / pageSize + 1;
        if((pageNumber - 1) * pageSize >= commentCount){
            return new JsonResponse<>(new Pages<>(totalNumber,commentCount, Collections.emptyList()),StatusCodeEnum.SUCCESS.getCode());
        }
        List<CommentVO> commentList = commentService.findCommentList(pageNumber,pageSize);
        return new JsonResponse<>(new Pages<>(totalNumber,commentCount,commentList),StatusCodeEnum.SUCCESS.getCode());
    }
}
