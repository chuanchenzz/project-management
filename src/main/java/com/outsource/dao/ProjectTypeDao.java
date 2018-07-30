package com.outsource.dao;

import com.outsource.model.ProjectTypeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 查找所有一级项目类型的id列表
     * @return
     */
    List<Integer> listMainProjectTypeId();

    /**
     * 获取获取指定一级项目下的二级项目id列表
     * @param mainId
     * @return
     */
    List<Integer> listChildProjectTypeId(@Param("mainId") int mainId);
}
