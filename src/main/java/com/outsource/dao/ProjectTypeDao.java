package com.outsource.dao;

import com.outsource.model.ProjectTypeDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author chuanchen
 */
public interface ProjectTypeDao {
    /**
     * 根据id查找项目类型
     * @param id
     * @return
     */
    ProjectTypeDO getProjectTypeById(@Param("id") int id);

    /**
     * 根据name查找项目类型
     * @param name
     * @return
     */
    ProjectTypeDO getProjectTypeByName(@Param("name") String name);

    /**
     * 添加项目类型
     * @param projectType
     * @return
     */
    int insertProjectType(ProjectTypeDO projectType);

    /**
     * 更新项目类型
     * @param projectType
     * @return
     */
    int updateProjectType(ProjectTypeDO projectType);
}
