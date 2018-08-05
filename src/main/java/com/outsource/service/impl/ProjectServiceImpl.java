package com.outsource.service.impl;

import com.outsource.dao.ProjectDao;
import com.outsource.dao.ProjectTypeDao;
import com.outsource.model.*;
import com.outsource.service.IProjectService;
import com.outsource.util.KeyUtil;
import com.outsource.util.RedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author chuanchen
 */
@Service
public class ProjectServiceImpl implements IProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
    @Autowired
    RedisOperation redisOperation;
    @Autowired
    ProjectTypeDao projectTypeDao;
    @Autowired
    ProjectDao projectDao;

    @Override
    public ProjectTypeDO findProjectType(int id) {
        String projectTypeKey = KeyUtil.generateKey(RedisKey.PROJECT_TYPE, id);
        ProjectTypeDO projectTypeDO = (ProjectTypeDO) redisOperation.get(projectTypeKey);
        if (projectTypeDO == null) {
            projectTypeDO = projectTypeDao.getProjectTypeById(id);
            if (projectTypeDO == null) {
                logger.info("projectType not found, id:{}", id);
                return null;
            }
            redisOperation.set(projectTypeKey, projectTypeDO);
        }
        return projectTypeDO;
    }

    @Override
    public ProjectTypeDO addProjectType(String name, String description, int parentId) {
        ProjectTypeDO projectTypeDO = new ProjectTypeDO(name, description, parentId);
        projectTypeDO.setStatus(ProjectTypeDO.StatusEnum.DISPLAY.getCode());
        projectTypeDO.setCreateTime(new Date());
        try {
            projectTypeDao.insertProjectType(projectTypeDO);
        } catch (Exception e) {
            logger.error(String.format("insert projectType error! projectType:%s", projectTypeDO), e);
            return null;
        }
        String projectTypeKey = KeyUtil.generateKey(RedisKey.PROJECT_TYPE, projectTypeDO.getId());
        redisOperation.set(projectTypeKey, projectTypeDO);
        if(parentId == 0){
            redisOperation.addZSetItem(RedisKey.PROJECT_TYPE_MAIN_ID_LIST,projectTypeDO.getId(),projectTypeDO.getCreateTime().getTime());
            if(redisOperation.hasKey(RedisKey.PROJECT_TYPE_MAIN_ID_LIST_NOT_EXIST)){
                redisOperation.deleteKey(RedisKey.PROJECT_TYPE_MAIN_ID_LIST_NOT_EXIST);
            }
        }
        if (parentId != 0) {
            String childIdListKey = KeyUtil.generateKey(RedisKey.PROJECT_TYPE_CHILD_ID_LIST, parentId);
            redisOperation.addZSetItem(childIdListKey, projectTypeDO.getId(), projectTypeDO.getCreateTime().getTime());
        }
        return projectTypeDO;
    }

    @Override
    public ProjectTypeDO findProjectType(String name) {
        try {
            return projectTypeDao.getProjectTypeByName(name);
        } catch (Exception e) {
            logger.error(String.format("error! name:%s", name), e);
            return null;
        }
    }

    @Override
    public ProjectTypeDO updateProjectType(int id, String name, String description) {
        ProjectTypeDO newProjectType = new ProjectTypeDO();
        newProjectType.setId(id);
        newProjectType.setName(name);
        newProjectType.setDescription(description);
        int updateResult = projectTypeDao.updateProjectType(newProjectType);
        if (updateResult <= 0) {
            logger.warn("update projectType fail! maybe not exist! id:{}", id);
            return null;
        }
        ProjectTypeDO oldProjectType = findProjectType(id);
        oldProjectType.setName(name);
        oldProjectType.setDescription(description);
        String projectTypeKey = KeyUtil.generateKey(RedisKey.PROJECT_TYPE, id);
        redisOperation.set(projectTypeKey, oldProjectType);
        return oldProjectType;
    }

    @Override
    public Integer deleteProjectType(int id) {
        ProjectTypeDO projectTypeDO = findProjectType(id);
        if(projectTypeDO == null){
            logger.warn("project type not found! id:{}",id);
            return null;
        }
        Integer parentId = projectTypeDO.getParentId();
        if(parentId > 0){
            String projectIdListKey = KeyUtil.generateKey(RedisKey.PROJECT_ID_LIST,id);
            if(redisOperation.hasKey(projectIdListKey)){
                logger.info("project type has project,can't delete, id:{}",id);
                return null;
            }
        }else {
            String mainProjectTypeKey = KeyUtil.generateKey(RedisKey.PROJECT_TYPE_CHILD_ID_LIST,id);
            if(redisOperation.hasKey(mainProjectTypeKey)){
                logger.info("main project type has child type, id:{}",id);
                return null;
            }
        }
        int updateId = projectTypeDao.updateStatus(id,ProjectTypeDO.StatusEnum.HIDE.getCode());
        if(updateId <= 0){
            logger.warn("update fail! maybe project type not exist! id:{}",id);
            return null;
        }
        String projectTypeKey = KeyUtil.generateKey(RedisKey.PROJECT_TYPE,id);
        projectTypeDO.setStatus(ProjectTypeDO.StatusEnum.HIDE.getCode());
        redisOperation.set(projectTypeKey,projectTypeDO);
        if(parentId == 0){
            if(redisOperation.removeZSetEntry(RedisKey.PROJECT_TYPE_MAIN_ID_LIST,id) == null){
                logger.warn("delete main id from ZSet! id:{}",id);
            }
        }else {
            String childProjectTypeListKey = KeyUtil.generateKey(RedisKey.PROJECT_TYPE_CHILD_ID_LIST,parentId);
            if(redisOperation.removeZSetEntry(childProjectTypeListKey,id) == null){
                logger.warn("delete child id from ZSet! id:{}",id);
            }
        }
        return id;
    }

    @Override
    public List<ProjectTypeDO> findMainProjectTypeList() {
        Set<Integer> mainIdSet = redisOperation.getZSet(RedisKey.PROJECT_TYPE_MAIN_ID_LIST);
        if (mainIdSet == null) {
            if (redisOperation.hasKey(RedisKey.PROJECT_TYPE_MAIN_ID_LIST_NOT_EXIST)) {
                return Collections.emptyList();
            } else {
                List<Integer> mainIdList = projectTypeDao.listMainProjectTypeId();
                if (CollectionUtils.isEmpty(mainIdList)) {
                    redisOperation.set(RedisKey.PROJECT_TYPE_MAIN_ID_LIST_NOT_EXIST,1);
                    return Collections.emptyList();
                } else {
                    mainIdSet = new TreeSet<>(mainIdList);
                }
            }
        }
        List<ProjectTypeDO> projectTypeList = new ArrayList<>(mainIdSet.size());
        for (Integer id : mainIdSet) {
            ProjectTypeDO projectType = findProjectType(id);
            if (projectType != null) {
                projectTypeList.add(projectType);
            }
        }
        return projectTypeList;
    }

    @Override
    public List<ProjectTypeDO> findChildProjectTypeList(int mainProjectTypeId) {
        String childListKey = KeyUtil.generateKey(RedisKey.PROJECT_TYPE_CHILD_ID_LIST, mainProjectTypeId);
        Set<Integer> childSetId = redisOperation.getZSet(childListKey);
        if (CollectionUtils.isEmpty(childSetId)) {
            List<Integer> childIdList = projectTypeDao.listChildProjectTypeId(mainProjectTypeId);
            if (CollectionUtils.isEmpty(childIdList)) {
                return Collections.emptyList();
            }
            for (Integer id : childIdList) {
                redisOperation.addZSetItem(childListKey, id, id);
            }
            childSetId = new TreeSet<>(childIdList);
        }
        List<ProjectTypeDO> projectTypeDOList = new ArrayList<>(childSetId.size());
        for (Integer id : childSetId) {
            ProjectTypeDO projectType = findProjectType(id);
            if (projectType != null) {
                projectTypeDOList.add(projectType);
            }
        }
        return projectTypeDOList;
    }

    @Override
    public List<ProjectTypeVO> findProjectTypeList() {
        List<ProjectTypeDO> mainProjectTypeList = findMainProjectTypeList();
        if (CollectionUtils.isEmpty(mainProjectTypeList)) {
            return Collections.emptyList();
        }
        List<ProjectTypeVO> projectTypeVOList = new ArrayList<>(mainProjectTypeList.size());
        for (ProjectTypeDO projectTypeDO : mainProjectTypeList) {
            ProjectTypeVO projectTypeVO = new ProjectTypeVO(projectTypeDO);
            projectTypeVO.setChildProjectTypeList(findChildProjectTypeList(projectTypeDO.getId()));
            projectTypeVOList.add(projectTypeVO);
        }
        return projectTypeVOList;
    }

    @Override
    public ProjectVO addProject(ProjectDO projectDO) {
        projectDO.setTime(new Date());
        projectDO.setDisplayStatus(ProjectDO.DisplayStatusEnum.NOT_DISPLAY.code);
        try {
            projectDao.insertProject(projectDO);
        } catch (Exception e) {
            logger.error(String.format("insert project fail! project:%s",projectDO),e);
            return null;
        }
        String projectKey = KeyUtil.generateKey(RedisKey.PROJECT,projectDO.getId());
        redisOperation.set(projectKey,projectDO);
        String projectIdListKey = KeyUtil.generateKey(RedisKey.PROJECT_ID_LIST,projectDO.getClassification());
        redisOperation.addZSetItem(projectIdListKey,projectDO.getId(),projectDO.getTime().getTime());
        return new ProjectVO(projectDO);
    }
}
