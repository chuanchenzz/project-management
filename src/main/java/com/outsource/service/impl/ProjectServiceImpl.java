package com.outsource.service.impl;

import com.outsource.dao.ProjectDao;
import com.outsource.dao.ProjectTypeDao;
import com.outsource.model.ProjectTypeDO;
import com.outsource.model.RedisKey;
import com.outsource.service.IProjectService;
import com.outsource.util.KeyUtil;
import com.outsource.util.RedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        if (parentId != 0) {
            String childIdListKey = KeyUtil.generateKey(RedisKey.PROJECT_TYPE_CHILD_ID_LIST, parentId);
            redisOperation.addZsetItem(childIdListKey, projectTypeDO.getId(), projectTypeDO.getCreateTime().getTime());
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
}
