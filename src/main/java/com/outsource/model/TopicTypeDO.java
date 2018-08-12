package com.outsource.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class TopicTypeDO implements Serializable{
    private static final long serialVerUID = -1L;

    private Integer id;
    private String name;
    private Date time;
    private Integer status;

    public TopicTypeDO(){}

    public TopicTypeDO(String name,Date time,Integer status){
        this.name = name;
        this.time = time;
        this.status = status;
    }

    public enum StatusEnum{
        /**
         * 显示状态
         */
        DISPLAY(1,"显示"),
        /**
         * 隐藏状态
         */
        HIDDEN(0,"隐藏");
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
