package com.outsource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class ProjectVO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String name;
    private String description;
    private String location;
    private String startMoney;
    private String endMoney;
    private String mobile;
    private String masterGraph;
    private String introduction;
    private String poster;
    private Integer classification;
    private Integer displayStatus;
    private Integer recommendLevel;
    private Date time;

    public ProjectVO(){}

    public ProjectVO(final ProjectDO projectDO){
        this.id = projectDO.getId();
        this.name = projectDO.getName();
        this.description = projectDO.getDescription();
        this.location = projectDO.getLocation();
        this.startMoney = projectDO.getStartMoney();
        this.endMoney = projectDO.getEndMoney();
        this.mobile = projectDO.getMobile();
        this.masterGraph = projectDO.getMasterGraph();
        this.introduction = projectDO.getIntroduction();
        this.poster = projectDO.getPoster();
        this.classification = projectDO.getClassification();
        this.displayStatus = projectDO.getDisplayStatus();
        this.recommendLevel = projectDO.getRecommendLevel();
        this.time = projectDO.getTime();
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

    public String getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(String startMoney) {
        this.startMoney = startMoney;
    }

    public String getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(String endMoney) {
        this.endMoney = endMoney;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMasterGraph() {
        return masterGraph;
    }

    public void setMasterGraph(String masterGraph) {
        this.masterGraph = masterGraph;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
    }

    public Integer getRecommendLevel() {
        return recommendLevel;
    }

    public void setRecommendLevel(Integer recommendLevel) {
        this.recommendLevel = recommendLevel;
    }

    @Override
    public String toString() {
        return "ProjectVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", startMoney='" + startMoney + '\'' +
                ", endMoney='" + endMoney + '\'' +
                ", mobile='" + mobile + '\'' +
                ", masterGraph='" + masterGraph + '\'' +
                ", introduction='" + introduction + '\'' +
                ", poster='" + poster + '\'' +
                ", classification=" + classification +
                ", displayStatus=" + displayStatus +
                ", recommendLevel=" + recommendLevel +
                ", time=" + time +
                '}';
    }
}
