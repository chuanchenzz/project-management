package com.outsource.service.impl;

import com.outsource.dao.AdminDao;
import com.outsource.model.AdminDO;
import com.outsource.model.RedisKey;
import com.outsource.service.IAdminService;
import com.outsource.util.KeyUtil;
import com.outsource.util.RedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author chuanchen
 */
@Service
public class AdminServiceImpl implements IAdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Resource
    AdminDao adminDao;
    @Autowired
    RedisOperation redisOperation;

    @Override
    public Integer updateAdmin(int id, String password, int level) {
        int updateResult = adminDao.updateAdmin(id, password, level);
        if (updateResult <= 0) {
            logger.warn("update admin error! id:{}", id);
            return null;
        }
        String adminKey = KeyUtil.generateKey(RedisKey.ADMIN, id);
        AdminDO admin = (AdminDO) redisOperation.get(adminKey);
        if (admin == null) {
            admin = adminDao.getAdminById(id);
            if (admin == null) {
                logger.warn("admin not found! id:{}", id);
                return null;
            }
        } else {
            admin.setLevel((byte) level);
            admin.setPassport(password);
        }
        redisOperation.set(adminKey, admin);
        return id;
    }
}
