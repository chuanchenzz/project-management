package com.outsource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class TopicVO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String title;
    private Date pubTime;
    private String authorName;
    private String content;
    private Integer classification;
    private Integer scanCount;
    private Integer displayStatus;

    public TopicVO(){}

    public TopicVO(final TopicDO topicDO){
        this.id = topicDO.getId();
        this.title = topicDO.getTitle();
        this.pubTime = topicDO.getPubTime();
        this.authorName = topicDO.getAuthorName();
        this.content = topicDO.getContent();
        this.classification = topicDO.getClassification();
        this.scanCount = 0;
        this.displayStatus = topicDO.getDisplayStatus();
    }

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

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Integer getScanCount() {
        return scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
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

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public Integer getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
    }

    @Override
    public String toString() {
        return "TopicVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pubTime=" + pubTime +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                ", classification=" + classification +
                ", scanCount=" + scanCount +
                ", displayStatus=" + displayStatus +
                '}';
    }
}
