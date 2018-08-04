package com.outsource.model;

import java.io.Serializable;

/**
 * @author chuanchen
 */
public class ProjectVO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String name;
    private String description;
    private String location;
    private Integer classification;
    private Integer recommendLevel;
    private Integer displayStatus;

    public ProjectVO(){}

    public ProjectVO(final ProjectDO projectDO){
        this.id = projectDO.getId();
        this.name = projectDO.getName();
        this.description = projectDO.getDescription();
        this.location = projectDO.getLocation();
        this.classification = projectDO.getClassification();
        this.recommendLevel = projectDO.getRecommendLevel();
        this.displayStatus = projectDO.getDisplayStatus();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public Integer getRecommendLevel() {
        return recommendLevel;
    }

    public void setRecommendLevel(Integer recommendLevel) {
        this.recommendLevel = recommendLevel;
    }

    public Integer getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
    }

    @Override
    public String toString() {
        return "ProjectVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", classification=" + classification +
                ", recommendLevel=" + recommendLevel +
                ", displayStatus=" + displayStatus +
                '}';
    }
}
