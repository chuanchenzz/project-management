package com.outsource.service.impl;

import com.outsource.dao.AdminDao;
import com.outsource.model.AdminDO;
import com.outsource.model.Constants;
import com.outsource.model.RedisKey;
import com.outsource.service.IAdminService;
import com.outsource.util.KeyUtil;
import com.outsource.util.RedisOperation;
import com.outsource.util.StringRedisOperation;
import com.outsource.util.StringUtils;
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
    @Autowired
    StringRedisOperation stringRedisOperation;

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

    @Override
    public String login(String account, String password) {
        AdminDO admin = adminDao.getAdminByAccountAndPassport(account,password);
        if(admin == null){
            logger.info("admin not found! account:{}, password:{}", account,password);
            return null;
        }
        String sessionKey = KeyUtil.generateKey(RedisKey.ADMIN_ID_SESSION,admin.getId());
        String lastSession = stringRedisOperation.get(sessionKey);
        // 删除旧session
        if(StringUtils.isNotEmpty(lastSession)){
            String idKey = KeyUtil.generateKey(RedisKey.SESSION,lastSession);
            if(!stringRedisOperation.delete(idKey)){
                logger.warn("session is not found! maybe expire! session:{}",lastSession);
            }
        }
        String nowSession = StringUtils.generateJsonSession(account);
        stringRedisOperation.set(sessionKey,nowSession);
        String nowIdKey = KeyUtil.generateKey(RedisKey.SESSION,nowSession);
        stringRedisOperation.setExpire(nowIdKey,String.valueOf(admin.getId()), Constants.ADMIN_SEESION_EXPIRE_TIME);
        return nowSession;
    }
}
