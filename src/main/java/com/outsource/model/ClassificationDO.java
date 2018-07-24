package com.outsource.model;

import java.io.Serializable;

/**
 * @author chuanchen
 */
public class ClassificationDO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String name;
    private String description;
    private Integer parentId;
    private Integer type;

    public ClassificationDO(){}

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ClassificationDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parentId=" + parentId +
                ", type=" + type +
                '}';
    }
}
