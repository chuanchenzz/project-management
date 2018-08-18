package com.outsource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class TopicTypeVO implements Serializable{
    private static final long serialVersionUID = -1L;
    private Integer id;
    private String name;
    private Date time;
    private Integer topicCount;

    public TopicTypeVO(){}

    public TopicTypeVO(final TopicTypeDO topicTypeDO){
        this.id = topicTypeDO.getId();
        this.name = topicTypeDO.getName();
        this.time = topicTypeDO.getTime();
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    @Override
    public String toString() {
        return "TopicTypeVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", topicCount=" + topicCount +
                '}';
    }
}
