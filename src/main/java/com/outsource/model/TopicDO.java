package com.outsource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class TopicDO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String title;
    private Date pubTime;
    private String authorName;
    private String content;
    private Integer displayStatus;
    private Integer classification;

    public TopicDO(){}

    public TopicDO(String title, String authorName,String content,int classification){
        this.title = title;
        this.authorName = authorName;
        this.content = content;
        this.classification = classification;
    }

    public enum StatusEnum{
        /**
         * 显示状态
         */
        DISPLAY(1,"显示"),
        /**
         * 隐藏状态
         */
        HIDDEN(0,"隐藏"),
        /**
         * 删除状态
         */
        DELETE(2,"删除");
        public Integer statusCode;
        public String description;
        StatusEnum(Integer statusCode, String description){
            this.statusCode = statusCode;
            this.description =description;
        }

        @Override
        public String toString() {
            return "StatusEnum{" +
                    "statusCode=" + statusCode +
                    ", description='" + description + '\'' +
                    '}';
        }
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

    public void update(final TopicDO topicDO){
        if(topicDO == null){
            return;
        }
        if(topicDO.getTitle() != null){
            this.title = topicDO.getTitle();
        }
        if(topicDO.getAuthorName() != null){
            this.authorName = topicDO.getAuthorName();
        }
        if(topicDO.getClassification() != null){
            this.classification = topicDO.getClassification();
        }
        if(topicDO.getContent() != null){
            this.content = topicDO.getContent();
        }
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
