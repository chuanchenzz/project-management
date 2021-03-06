package com.outsource.service.impl;

import com.outsource.dao.AdminDao;
import com.outsource.model.AdminDO;
import com.outsource.model.AdminVO;
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
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
    public Integer updateAdmin(AdminDO adminDO) {

        int updateResult = adminDao.updateAdmin(adminDO);
        if (updateResult <= 0) {
            logger.warn("update admin error! admin:{}", adminDO);
            return null;
        }
        Integer id = adminDO.getId();
        String adminKey = KeyUtil.generateKey(RedisKey.ADMIN, id);
        AdminDO admin = (AdminDO) redisOperation.get(adminKey);
        if (admin == null) {
            admin = adminDao.getAdminById(id);
            if (admin == null) {
                logger.warn("admin not found! id:{}", id);
                return null;
            }
        } else {
            if(adminDO.getLevel() != null) {
                admin.setLevel(adminDO.getLevel());
            }
            if(!StringUtils.isEmpty(adminDO.getPassword())) {
                admin.setPassword(adminDO.getPassword());
            }
        }
        redisOperation.set(adminKey, admin);
        return id;
    }

    @Override
    public String login(String account, String password) {
        AdminDO admin = adminDao.getAdminByAccountAndPassport(account, password);
        if (admin == null) {
            logger.info("admin not found! account:{}, password:{}", account, password);
            return null;
        }
        String sessionKey = KeyUtil.generateKey(RedisKey.ADMIN_ID_SESSION, admin.getId());
        String lastSession = stringRedisOperation.get(sessionKey);
        // 删除旧session
        if (StringUtils.isNotEmpty(lastSession)) {
            String idKey = KeyUtil.generateKey(RedisKey.SESSION, lastSession);
            stringRedisOperation.delete(idKey);
        }
        String nowSession = StringUtils.generateJsonSession(account);
        stringRedisOperation.set(sessionKey, nowSession);
        String nowIdKey = KeyUtil.generateKey(RedisKey.SESSION, nowSession);
        stringRedisOperation.setExpire(nowIdKey, String.valueOf(admin.getId()), Constants.ADMIN_SEESION_EXPIRE_TIME);
        return nowSession;
    }

    @Override
    public AdminVO findAdmin(String account) {
        AdminDO admin = adminDao.getAdminByAccount(account);
        if (admin != null) {
            return new AdminVO(admin.getId(), admin.getAccount(),admin.getLevel());
        } else {
            return null;
        }
    }

    @Override
    public AdminVO findAdmin(int id) {
        String adminKey = KeyUtil.generateKey(RedisKey.ADMIN, id);
        AdminDO admin = (AdminDO) redisOperation.get(adminKey);
        if (admin == null) {
            admin = adminDao.getAdminById(id);
            if (admin == null) {
                logger.warn("admin not found! id:{}", id);
                return null;
            }
            redisOperation.set(adminKey, admin);
        }
        return new AdminVO(admin.getId(), admin.getAccount(),admin.getLevel());
    }

    @Override
    public AdminVO addAdmin(String account, String password, int level) {
        AdminDO admin = new AdminDO(account, password, level);
        try {
            adminDao.insertAdmin(admin);
        } catch (Exception e) {
            logger.error(String.format("add admin error! account:%s,password:%s,level:%d", account, password, level), e);
            return null;
        }
        String adminKey = KeyUtil.generateKey(RedisKey.ADMIN, admin.getId());
        redisOperation.set(adminKey, admin);
        return new AdminVO(admin.getId(), account,level);
    }

    @Override
    public List<AdminVO> findAdminList() {
        List<AdminDO> adminDOList;
        try {
            adminDOList = adminDao.listAdmin();
        } catch (Exception e) {
            logger.error("find admin list error!",e);
            return null;
        }
        if (CollectionUtils.isEmpty(adminDOList)) {
            return Collections.emptyList();
        }
        List<AdminVO> adminVOList = new ArrayList<>(adminDOList.size());
        for (AdminDO adminDO : adminDOList) {
            adminVOList.add(new AdminVO(adminDO.getId(), adminDO.getAccount(),adminDO.getLevel()));
        }
        return adminVOList;
    }

    @Override
    public Integer deleteAdmin(int id) {
        int deleteRow = adminDao.deleteAdmin(id);
        if(deleteRow <= 0){
            logger.warn("delete admin fail, may be admin not found! id:{}",id);
            return null;
        }
        String adminKey = KeyUtil.generateKey(RedisKey.ADMIN,id);
        redisOperation.deleteKey(adminKey);
        String sessionKey = KeyUtil.generateKey(RedisKey.ADMIN_ID_SESSION,id);
        if(stringRedisOperation.hasKey(sessionKey)){
            String expireSession = stringRedisOperation.get(sessionKey);
            stringRedisOperation.delete(sessionKey);
            String idKey = KeyUtil.generateKey(RedisKey.SESSION,expireSession);
            stringRedisOperation.delete(idKey);
        }
        return id;
    }
}
