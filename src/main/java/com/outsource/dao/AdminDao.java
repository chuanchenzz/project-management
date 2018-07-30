package com.outsource.dao;

import com.outsource.model.AdminDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chuanchen
 */
public interface AdminDao {
    /**
     * 更新管理员信息
     * @param id
     * @param password
     * @param level
     * @return
     */
    int updateAdmin(@Param("id") int id, @Param("password") String password, @Param("level") int level);

    /**
     * 通过id获取AmidnDO对象
     * @param id
     * @return
     */
    AdminDO getAdminById(@Param("id") int id);

    /**
     * 根据账号和密码查找用户
     * @param account
     * @param password
     * @return
     */
    AdminDO getAdminByAccountAndPassport(@Param("account") String account, @Param("password") String password);

    /**
     * 根据用户名查找用户
     * @param account
     * @return
     */
    AdminDO getAdminByAccount(@Param("account") String account);

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    Integer insertAdmin(AdminDO admin);

    /**
     * 获取所有的管理员
     * @return
     */
    List<AdminDO>  listAdmin();
}
