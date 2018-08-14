package com.outsource.service.impl;

import com.outsource.dao.WebsiteDao;
import com.outsource.model.RedisKey;
import com.outsource.model.Website;
import com.outsource.service.IWebsiteService;
import com.outsource.util.RedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author chuanchen
 */
@Service
public class WebsiteServiceImpl implements IWebsiteService {
    private static final Logger logger = LoggerFactory.getLogger(WebsiteServiceImpl.class);
    @Autowired
    WebsiteDao websiteDao;
    @Autowired
    RedisOperation redisOperation;

    @Override
    public Website updateOrAddWebsiteConfigIfAbsent(String name, String favIcon, String logo) {
        Website website = findWebsiteConfig();
        if(website == null){
            website = new Website(name,favIcon,logo);
            website.setId(1);
            website.setTime(new Date());
            websiteDao.addWebsiteConfig(website);
        }else {
            website.setTime(new Date());
            website.setName(name);
            website.setFavIcon(favIcon);
            website.setLogo(logo);
            int updateResult = websiteDao.updateWebsiteConfig(website);
            if(updateResult <= 0){
                return null;
            }
        }
        redisOperation.set(RedisKey.WEBSITE,website);
        return website;
    }

    @Override
    public Website findWebsiteConfig() {
        Website website = (Website) redisOperation.get(RedisKey.WEBSITE);
        if(website == null){
            List<Website> websiteList = websiteDao.getWebsite();
            if(CollectionUtils.isEmpty(websiteList)){
                return null;
            }
            website = websiteList.get(0);
            redisOperation.set(RedisKey.WEBSITE,website);
        }
        return website;
    }
}
