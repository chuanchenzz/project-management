package com.outsource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuanchen
 */
public class CommentDO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String name;
    private String mobile;
    private String email;
    private Integer projectId;
    private String content;
    private Integer status;
    private String customerRemark;
    private Date time;

    public CommentDO(){}

    public enum  DisplayStatusEnum{
        /**
         * 不显示
         */
        NOT_DISPLAY(0,"不显示"),
        /**
         * 显示
         */
        DISPLAY(1,"显示");

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CommentDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", projectId=" + projectId +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", customerRemark='" + customerRemark + '\'' +
                ", time=" + time +
                '}';
    }
}
