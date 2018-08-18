package com.outsource.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author chuanchen
 */
public class ProjectTypeVO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String name;
    private String description;
    private Integer parentId;
    private Integer status;
    private List<ProjectTypeVO> childProjectTypeList;
    private Integer projectCount;

    public ProjectTypeVO(){}

    public ProjectTypeVO(final ProjectTypeDO projectTypeDO){
        this.id = projectTypeDO.getId();
        this.name = projectTypeDO.getName();
        this.description = projectTypeDO.getDescription();
        this.parentId = projectTypeDO.getParentId();
        this.status = projectTypeDO.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ProjectTypeVO> getChildProjectTypeList() {
        return childProjectTypeList;
    }

    public void setChildProjectTypeList(List<ProjectTypeVO> childProjectTypeList) {
        this.childProjectTypeList = childProjectTypeList;
    }

    public Integer getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(Integer projectCount) {
        this.projectCount = projectCount;
    }

    @Override
    public String toString() {
        return "ProjectTypeVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parentId=" + parentId +
                ", status=" + status +
                ", childProjectTypeList=" + childProjectTypeList +
                ", projectCount=" + projectCount +
                '}';
    }
}
