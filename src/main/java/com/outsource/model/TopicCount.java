package com.outsource.model;

import java.io.Serializable;

/**
 * @author chuanchen
 */
public class TopicCount implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer topicId;
    private Integer scanCount;

    public TopicCount(int topicId){
        this.topicId = topicId;
        this.scanCount = 0;
    }
    public TopicCount(int topicId, int scanCount){
        this.topicId = topicId;
        this.scanCount = scanCount;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getScanCount() {
        return scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
    }

    @Override
    public String toString() {
        return "TopicCount{" +
                "topicId=" + topicId +
                ", scanCount=" + scanCount +
                '}';
    }
}
