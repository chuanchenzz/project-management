package com.outsource.service.impl;

import com.outsource.dao.ProjectDao;
import com.outsource.dao.ProjectTypeDao;
import com.outsource.model.ProjectTypeDO;
import com.outsource.model.ProjectTypeVO;
import com.outsource.model.RedisKey;
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
    public List<ProjectTypeDO> findMainProjectTypeList() {
        Set<Integer> mainIdSet = redisOperation.getZSet(RedisKey.PROJECT_TYPE_MAIN_ID_LIST);
        if (mainIdSet == null) {
            if (redisOperation.hasKey(RedisKey.PROJECT_TYPE_MAIN_ID_LIST_EXIST)) {
                return Collections.emptyList();
            } else {
                List<Integer> mainIdList = projectTypeDao.listMainProjectTypeId();
                if (CollectionUtils.isEmpty(mainIdList)) {
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
        }
        return projectTypeVOList;
    }
}
