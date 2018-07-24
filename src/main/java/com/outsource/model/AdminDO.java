package com.outsource.model;

import java.io.Serializable;

/**
 * @author chuanchen
 */
public class AdminDO implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String account;
    private String passport;
    /**
     * 后三位代表权限(第一位代表项目管理,第二位代表文章管理,第三位代表留言管理)
     */
    private Byte level;

    public AdminDO(){}

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

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "AdminDO{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", passport='" + passport + '\'' +
                ", level=" + level +
                '}';
    }
}
