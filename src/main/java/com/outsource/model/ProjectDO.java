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
    private String startMoney;
    private String endMoney;
    private String mobile;
    private String masterGraph;
    private String introduction;
    private String poster;
    private Integer classification;
    private Integer recommendLevel;
    private Integer displayStatus;
    private Date time;

    public ProjectDO(){}

    public enum  DisplayStatusEnum{
        /**
         * 不显示
         */
        NOT_DISPLAY(0,"不显示"),
        /**
         * 显示
         */
        DISPLAY(1,"显示"),

        DELETE(2,"删除");

        DisplayStatusEnum(int code, String description){
            this.code = code;
            this.description = description;
        }
        public Integer code;
        public String description;
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

    public void update(ProjectDO projectDO){
        if(projectDO == null){
            return;
        }
        if(projectDO.getName() != null){
            this.name = projectDO.getName();
        }
        if(projectDO.getDescription() != null){
            this.description = projectDO.getDescription();
        }
        if(projectDO.getLocation() != null){
            this.location = projectDO.getLocation();
        }
        if(projectDO.getStartMoney() != null){
            this.startMoney = projectDO.getStartMoney();
        }
        if(projectDO.getEndMoney() != null){
            this.endMoney = projectDO.getEndMoney();
        }
        if(projectDO.getMobile() != null){
            this.mobile = projectDO.getMobile();
        }
        if(projectDO.getMasterGraph() != null){
            this.masterGraph = projectDO.getMasterGraph();
        }
        if(projectDO.getIntroduction() != null){
            this.introduction = projectDO.getIntroduction();
        }
        if(projectDO.getPoster() != null){
            this.poster = projectDO.getPoster();
        }
        if(projectDO.getRecommendLevel() != null){
            this.recommendLevel = projectDO.getRecommendLevel();
        }
    }

    @Override
    public String toString() {
        return "ProjectDO{" +
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
                ", recommendLevel=" + recommendLevel +
                ", displayStatus=" + displayStatus +
                ", time=" + time +
                '}';
    }
}
