package com.outsource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class ProjectTypeDO implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String name;
    private String description;
    private Integer parentId;
    private Integer status;
    private Date createTime;


    public ProjectTypeDO() {
    }

    public ProjectTypeDO(String name, String description, int parentId) {
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

    public enum StatusEnum {
        /**
         * 显示状态
         */
        DISPLAY(1, "展示"),
        /**
         * 隐藏状态
         */
        HIDE(0, "隐藏");

        StatusEnum(int code, String description) {
            this.code = code;
            this.description = description;
        }

        private Integer code;
        private String description;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ProjectTypeDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parentId=" + parentId +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
