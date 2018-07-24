package com.outsource.service;

/**
 * @author chuanchen
 */
public interface IAdminService {
    /**
     * 更新密码或者权限
     * @param id
     * @param password
     * @param level
     * @return
     */
    Integer updateAdmin(int id, String password, int level);
}
