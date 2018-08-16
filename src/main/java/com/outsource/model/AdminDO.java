package com.outsource.model;

import java.io.Serializable;

/**
 * @author chuanchen
 */
public class AdminDO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String account;
    private String password;
    /**
     * 后三位代表权限(第一位代表项目管理,第二位代表文章管理,第三位代表留言管理)
     */
    private Integer level;

    public AdminDO(){}

    public AdminDO(int id){
        this.id = id;
    }

    public AdminDO(String account, String password, Integer level){
        this.account = account;
        this.password = password;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "AdminDO{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                '}';
    }
}
