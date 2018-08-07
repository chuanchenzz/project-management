package com.outsource.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class TopicTypeDO implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(TopicTypeDO.class);

    private Integer id;
    private String name;
    private Date time;
    private Integer status;

    public TopicTypeDO(){}

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TopicTypeDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", status=" + status +
                '}';
    }
}
