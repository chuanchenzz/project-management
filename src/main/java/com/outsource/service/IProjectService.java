package com.outsource.service;

import com.outsource.model.ProjectDO;
import com.outsource.model.ProjectTypeDO;
import com.outsource.model.ProjectTypeVO;
import com.outsource.model.ProjectVO;

import java.util.List;

/**
 * @author chuanchen
 */
public interface IProjectService {
    /**
     * 通过id查找projectType对象
     * @param id
     * @return
     */
    ProjectTypeDO findProjectType(int id);

    /**
     * 添加分类
     * @param name
     * @param description
     * @param parentId
     * @return
     */
    ProjectTypeDO addProjectType(String name, String description, int parentId);

    /**
     * 通过name查找projectType对象
     * @param name
     * @return
     */
    ProjectTypeDO findProjectType(String name);

    /**
     * 修改项目类型
     * @param id
     * @param name
     * @param description
     * @return
     */
    ProjectTypeDO updateProjectType(int id, String name, String description);

    /**
     * 删除项目类型
     * @param id
     * @return
     */
    Integer deleteProjectType(int id);

    /**
     * 获取所有一级项目类型
     * @return
     */
    List<ProjectTypeDO> findMainProjectTypeList();

    /**
     * 获取指定的一级项目的二级项目列表
     * @param mainProjectTypeId
     * @return
     */
    List<ProjectTypeVO> findChildProjectTypeList(int mainProjectTypeId);

    /**
     * 获取所有的项目类型
     * @return
     */
    List<ProjectTypeVO> findProjectTypeList();

    /**
     * 添加项目
     * @param projectDO
     * @return
     */
    ProjectVO addProject(ProjectDO projectDO);

    /**
     * 更新项目
     * @param projectDO
     * @return
     */
    Integer updateProject(ProjectDO projectDO);

    /**
     * 审核项目
     * @param id
     * @return
     */
    Integer auditProject(int id, int status);

    /**
     * 通过id查找项目
     * @param id
     * @return
     */
    ProjectDO findProject(int id);

    /**
     * 查找特定项目类型的项目数
     * @param projectTypeId
     * @return
     */
    Integer findProjectCount(int projectTypeId);

    /**
     * 分页查找项目
     * @param projectTypeId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ProjectVO> findProjectList(int projectTypeId, int pageNumber, int pageSize);

    /**
     * 分页查找项目id列表
     * @param projectTypeId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Integer> findProjectIdList(int projectTypeId, int pageNumber, int pageSize);

    /**
     * 删除项目
     * @param projectId
     * @return
     */
    Integer deleteProject(int projectId);
}
