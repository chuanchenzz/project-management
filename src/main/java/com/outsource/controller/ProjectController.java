package com.outsource.controller;

import com.outsource.aop.AuthLevel;
import com.outsource.constant.StatusCodeEnum;
import com.outsource.interceptor.AuthEnum;
import com.outsource.model.*;
import com.outsource.service.IProjectService;
import com.outsource.util.ImageUtils;
import com.outsource.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author chuanchen
 */
@RestController
@RequestMapping("/project")
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    IProjectService projectService;
    private static final String IMAGE_DIR = "/opt/dir/images/";

    @RequestMapping(value = "/type", method = RequestMethod.POST)
    public JsonResponse<ProjectTypeDO> addProjectType(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam(value = "parent_id", defaultValue = "0") int parentId) {
        boolean isValidParams = StringUtils.isNotEmpty(name, description) && parentId >= 0;
        if (!isValidParams) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), StatusCodeEnum.PARAMETER_ERROR.getDescription());
        }
        // 一级分类校验
        if (parentId > 0) {
            ProjectTypeDO parentProjectType = projectService.findProjectType(parentId);
            if (parentProjectType == null) {
                return new JsonResponse<>(StatusCodeEnum.NOT_FOUND.getCode(), "一级分类不存在!");
            } else if (parentProjectType.getParentId() != 0) {
                return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "一级分类id错误!");
            }
        }
        // 校验项目类型是否存在
        ProjectTypeDO projectType = projectService.findProjectType(name);
        if (projectType != null && projectType.getStatus().equals(ProjectTypeDO.StatusEnum.DISPLAY)) {
            logger.warn("the projectType has exist,name:{}", name);
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "项目类型已经存在!");
        }
        projectType = projectService.addProjectType(name, description, parentId);
        if (projectType == null) {
            return new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!");
        }
        return new JsonResponse<>(projectType, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/type/{id}", method = RequestMethod.PUT)
    public JsonResponse<ProjectTypeDO> updateProjectType(@PathVariable("id") int id, @RequestParam("name") String name, @RequestParam("description") String description) {
        boolean isValidParams = StringUtils.isNotEmpty(name, description) && id > 0;
        if (!isValidParams) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        ProjectTypeDO oldProjectType = projectService.findProjectType(id);
        if (oldProjectType == null) {
            return new JsonResponse<>(StatusCodeEnum.NOT_FOUND.getCode(), "项目类型不存在!");
        }
        if (oldProjectType.getParentId() == 0 && !name.equals(oldProjectType.getName())) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "一级项目类型不允许修改名称!");
        }
        ProjectTypeDO projectType = projectService.updateProjectType(id, name, description);
        if (projectType == null) {
            return new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!");
        }
        return new JsonResponse<>(projectType, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/type/delete/{id}", method = RequestMethod.POST)
    public JsonResponse<Integer> deleteProjectType(@PathVariable("id") Integer id) {
        if (id <= 0) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        Integer updateId = projectService.deleteProjectType(id);
        return updateId == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!") : new JsonResponse<>(id, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    public JsonResponse<List<ProjectTypeVO>> findProjectTypeList() {
        List<ProjectTypeVO> projectTypeVOList = projectService.findProjectTypeList();
        return new JsonResponse<>(projectTypeVOList, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponse<ProjectVO> addProject(@RequestParam("name") String name, @RequestParam("location") String location, @RequestParam("money") String money,
                                              @RequestParam("mobile") String mobile, @RequestParam("master_graph") String masterGraph, @RequestParam("introduction") String introduction,
                                              @RequestParam("poster") String poster, @RequestParam("classification") int classification, @RequestParam("description") String description,
                                              @RequestParam("recommend_level") int recommendLevel) {
        ProjectDO projectDO = projectArgsIsValid(name, location, money, mobile, masterGraph, introduction, poster, classification, description, recommendLevel);
        if (projectDO == null) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        ProjectVO projectVO = projectService.addProject(projectDO);
        return projectVO == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!") : new JsonResponse<>(projectVO, StatusCodeEnum.SUCCESS.getCode());
    }

    @AuthLevel
    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    public JsonResponse<Integer> updateProject(@PathVariable("id") int id,@RequestParam("name") String name, @RequestParam("location") String location, @RequestParam("money") String money,
                                               @RequestParam("mobile") String mobile, @RequestParam("master_graph") String masterGraph, @RequestParam("introduction") String introduction,
                                               @RequestParam("poster") String poster, @RequestParam("description") String description,
                                               @RequestParam("recommend_level") int recommendLevel){
        ProjectDO projectDO;
        if(id <= 0 || (projectDO = projectArgsIsValid(name, location, money, mobile, masterGraph, introduction, poster, 1, description, recommendLevel)) == null){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        Integer updateResult = projectService.updateProject(projectDO);
        return updateResult == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(),"内部错误!") : new JsonResponse<>(updateResult,StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/images/{imageName}", method = RequestMethod.GET)
    public ResponseEntity findImage(@PathVariable("imageName") String imageName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        if (StringUtils.isEmpty(imageName)) {
            return new ResponseEntity(null, headers, HttpStatus.NOT_FOUND);
        }
        String fileName = IMAGE_DIR + imageName;
        byte[] fileBytes = ImageUtils.getImage(fileName);
        return new ResponseEntity(fileBytes, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public Map<String, String> uploadImages(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            throw new IllegalArgumentException("images is empty!");
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            throw new IllegalArgumentException();
        }
        String filePath = ImageUtils.generateFileName(".png");
        //获取路径
        String absolutePath = ImageUtils.saveImg(multipartFile, IMAGE_DIR, filePath);
        if (absolutePath == null) {
            throw new IllegalArgumentException();
        }
        Map<String, String> resultMap = new HashMap<>(4);
        resultMap.put("state", "SUCCESS");
        resultMap.put("url", "http://140.143.2.99:8999/project/images/" + filePath);
        resultMap.put("title", filePath);
        resultMap.put("original", filePath);
        return resultMap;
    }

    @AuthLevel(type = AuthEnum.REVIEW_MANAGER)
    @RequestMapping(value = "/audit/{id}", method = RequestMethod.POST)
    public JsonResponse<Integer> auditProject(@PathVariable("id") int id, @RequestParam("status") int status) {
        if (status != ProjectDO.DisplayStatusEnum.DISPLAY.code && status != ProjectDO.DisplayStatusEnum.NOT_DISPLAY.code) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        Integer auditResult = projectService.auditProject(id, status);
        return auditResult == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!") : new JsonResponse<>(auditResult, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/pages",method = RequestMethod.GET)
    public JsonResponse<Pages<ProjectVO>> findProjectList(@RequestParam("project_type_id") int projectTypeId, @RequestParam("page_number") int pageNumber, @RequestParam("page_size") int pageSize){
        if(pageNumber <= 0 || pageSize <= 0){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        Integer totalCount = projectService.findProjectCount(projectTypeId);
        if(totalCount == null){
            return new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(),"内部错误!");
        }
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if((pageNumber - 1) * pageSize >= totalCount){
            return new JsonResponse<>(new Pages<>(totalPage,totalCount,Collections.emptyList()),StatusCodeEnum.SUCCESS.getCode());
        }
        List<ProjectVO> projectVOList = projectService.findProjectList(projectTypeId,pageNumber,pageSize);
        return new JsonResponse<>(new Pages<>(totalPage,totalCount,projectVOList),StatusCodeEnum.SUCCESS.getCode());
    }

    private ProjectDO projectArgsIsValid(String name, String location, String money, String mobile, String masterGraph, String introduction, String poster, int classification, String description, int recommendLevel) {
        if (StringUtils.isEmpty(name, location, money, mobile, masterGraph, introduction, poster, description)) {
            logger.warn("params is null! name:{}, location:{}, money:{},mobile:{},masterGraph:{},introduction:{},poster:{},description:{}", name, location, money, mobile, masterGraph, introduction, poster, description);
            return null;
        }
        if (Long.valueOf(money) < 0) {
            logger.warn("money param error! money:{}", money);
            return null;
        }
        if (classification <= 0) {
            logger.warn("classification param error! money:{}", money);
            return null;
        }
        ProjectTypeDO projectType = projectService.findProjectType(classification);
        if (projectType == null) {
            logger.warn("project type not found! id:{}", classification);
            return null;
        }
        if (recommendLevel < 0) {
            logger.warn("recommendLevel param error! money:{}", money);
            return null;
        }
        ProjectDO project = new ProjectDO();
        project.setName(name);
        project.setLocation(location);
        project.setMoney(money);
        project.setMobile(mobile);
        project.setMasterGraph(masterGraph);
        project.setIntroduction(introduction);
        project.setPoster(poster);
        project.setClassification(classification);
        project.setDescription(description);
        project.setRecommendLevel(recommendLevel);
        project.setTime(new Date());
        return project;
    }
}
