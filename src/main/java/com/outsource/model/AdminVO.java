package com.outsource.model;

import java.io.Serializable;

public class AdminVO implements Serializable{
    private static final long serialVersionUID = -1L;

    private String account;
    private Integer adminId;
    private Integer level;

    public AdminVO(){}

    public AdminVO(Integer adminId, String account,Integer level){
        this.adminId = adminId;
        this.account = account;
        this.level = level;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "AdminVO{" +
                "account='" + account + '\'' +
                ", adminId=" + adminId +
                ", level=" + level +
                '}';
    }
}
