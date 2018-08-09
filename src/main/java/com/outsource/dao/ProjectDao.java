package com.outsource.dao;

import com.outsource.model.ProjectDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chuanchen
 */
public interface ProjectDao {
    /**
     * 添加项目
     * @param projectDO
     * @return
     */
    int insertProject(ProjectDO projectDO);

    /**
     * 通过id查找项目
     * @param id
     * @return
     */
    ProjectDO findProjectById(@Param("id") int id);

    /**
     * 更新项目的显示状态
     * @param id
     * @param status
     * @return
     */
    int updateDisplayStatus(@Param("id") int id, @Param("status") int status);

    /**
     * 查找项目列表
     * @param projectTypeId
     * @return
     */
    List<ProjectDO> pagesProjectByTypeId(@Param("projectTypeId") int projectTypeId);
}
