package com.outsource.service;

import com.outsource.model.AdminVO;

import java.util.List;

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

    /**
     * 管理员登录
     * @param account
     * @param password
     * @return
     */
    String login(String account, String password);

    /**
     * 通过用户名查找用户信息
     * @param account
     * @return
     */
    AdminVO findAdmin(String account);

    /**
     * 根据id查找管理员
     * @param id
     * @return
     */
    AdminVO findAdmin(int id);

    /**
     * 添加用户
     * @param account
     * @param password
     * @param level
     * @return
     */
    AdminVO addAdmin(String account, String password, int level);

    /**
     * 获取所有管理员信息
     * @return
     */
    List<AdminVO> findAdminList();
}
