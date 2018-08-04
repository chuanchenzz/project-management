package com.outsource.interceptor;

/**
 * @author chuanchen
 */

public enum AuthEnum {
    /**
     * 超级管理员
     */
    SUPER_MANAGER("超级管理员"),
    /**
     * 账号管理
     */
    ACCOUNT_MANAGR("账号管理"),
    /**
     * 审核管理
     */
    REVIEW_MANAGER("审核管理"),

    /**
     * 普通管理员
     */
    NORMAL("普通管理员");
    private String description;

    AuthEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
