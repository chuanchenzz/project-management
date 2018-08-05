package com.outsource.dao;

import com.outsource.model.ProjectDO;

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
}
