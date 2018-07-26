package com.outsource.dao;

import com.outsource.model.AdminDO;
import org.apache.ibatis.annotations.Param;

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
}
