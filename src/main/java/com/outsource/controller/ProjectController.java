package com.outsource.controller;

import com.outsource.constant.StatusCodeEnum;
import com.outsource.model.JsonResponse;
import com.outsource.model.ProjectDO;
import com.outsource.model.ProjectTypeDO;
import com.outsource.model.ProjectTypeVO;
import com.outsource.service.IProjectService;
import com.outsource.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author chuanchen
 */
@RestController
@RequestMapping("/project")
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    IProjectService projectService;

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
        if (projectType != null) {
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

    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    public JsonResponse<List<ProjectTypeVO>> findProjectTypeList() {
        List<ProjectTypeVO> projectTypeVOList = projectService.findProjectTypeList();
        return new JsonResponse<>(projectTypeVOList, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public JsonResponse<Integer> addProject(@RequestParam("name") String name, @RequestParam("location") String location, @RequestParam("money") String money,
                                            @RequestParam("mobile") String mobile,@RequestParam("master_graph") String masterGraph,@RequestParam("introduction") String introduction,
                                            @RequestParam("poster") String poster,@RequestParam("classification") int classification,@RequestParam("description") String description,
                                            @RequestParam("recommend_level") int recommendLevel){
        ProjectDO projectDO = projectArgsIsValid(name,location,money,mobile,masterGraph,introduction,poster,classification,description,recommendLevel);
        if(projectDO == null){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        return new JsonResponse<>(1,StatusCodeEnum.SUCCESS.getCode());
    }

    private ProjectDO projectArgsIsValid(String name, String location,String money,String mobile, String masterGraph,String introduction,String poster,int classification,String description,int recommendLevel){
        if(StringUtils.isEmpty(name,location,money,mobile,masterGraph,introduction,poster,description)){
            logger.warn("params is null! name:{}, location:{}, money:{},mobile:{},masterGraph:{},introduction:{},poster:{},description:{}",name,location,money,mobile,masterGraph,introduction,poster,description);
            return null;
        }
        if(Long.valueOf(money) < 0){
            logger.warn("money param error! money:{}",money);
            return null;
        }
        if(classification <= 0){
            logger.warn("classification param error! money:{}",money);
            return null;
        }
        if(recommendLevel < 0){
            logger.warn("recommendLevel param error! money:{}",money);
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
