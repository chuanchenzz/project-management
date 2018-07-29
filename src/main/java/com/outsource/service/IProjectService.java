package com.outsource.service;

import com.outsource.model.ProjectTypeDO;

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
}
