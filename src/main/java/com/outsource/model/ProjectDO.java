package com.outsource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class ProjectDO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String name;
    private String description;
    private String location;
    private String money;
    private String mobile;
    private String masterGraph;
    private String introduction;
    private String poster;
    private Integer classification;
    private Integer recommendLevel;
    private Integer displayStatus;
    private Date time;

    public ProjectDO(){}

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ProjectDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", money='" + money + '\'' +
                ", mobile='" + mobile + '\'' +
                ", masterGraph='" + masterGraph + '\'' +
                ", introduction='" + introduction + '\'' +
                ", poster='" + poster + '\'' +
                ", classification=" + classification +
                ", recommendLevel=" + recommendLevel +
                ", displayStatus=" + displayStatus +
                ", time=" + time +
                '}';
    }
}
