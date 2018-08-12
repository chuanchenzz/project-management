package com.outsource.constant;

/**
 * 状态码
 * @author chuanchen
 */

public enum StatusCodeEnum {
    /**
     * 请求成功
     */
    SUCCESS(200,"请求成功!"),
    /**
     * 服务器错误
     */
    SERVER_ERROR(500,"服务器错误!"),

    /**
     * 参数错误
     */
    PARAMETER_ERROR(400,"参数错误!"),

    /**
     * 未找到
     */
    NOT_FOUND(404,"未找到!"),

    /**
     * 权限错误
     */
    AUTH_ERROR(407,"权限错误!"),

    /**
     * 用户未登陆
     */
    NOT_LOGIN(403,"用户未登陆!"),

    /**
     * 重复添加
     */
    REPEAT_ADD(444,"重复添加!");

    StatusCodeEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }
    private Integer code;
    private String description;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
