package com.outsource.model;

import java.io.Serializable;

public class TopicDO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String title;
    private String pubTime;
    private String authorName;
    private String content;
    private Integer displayStatus;
    private Integer classification;

    public TopicDO(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    @Override
    public String toString() {
        return "TopicDO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pubTime='" + pubTime + '\'' +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                ", displayStatus=" + displayStatus +
                ", classification=" + classification +
                '}';
    }
}
