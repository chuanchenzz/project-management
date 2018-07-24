package com.outsource.model;

import java.io.Serializable;

/**
 * @author chuanchen
 */
public class CommentDao implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String userName;
    private String mobile;
    private String email;
    private String time;
    private Integer projectId;
    private String content;
    private Integer effectiveStatus;
    private String mark;

    public CommentDao(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Integer getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(Integer effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "CommentDao{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", time='" + time + '\'' +
                ", projectId=" + projectId +
                ", content='" + content + '\'' +
                ", effectiveStatus=" + effectiveStatus +
                ", mark='" + mark + '\'' +
                '}';
    }
}
