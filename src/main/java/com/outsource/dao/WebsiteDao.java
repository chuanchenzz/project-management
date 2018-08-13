package com.outsource.dao;

import com.outsource.model.Website;

import java.util.List;

/**
 * @author chuanchen
 */
public interface WebsiteDao {
    /**
     * 获取网站配置信息
     * @return
     */
    List<Website> getWebsite();

    /**
     * 配置网站信息
     * @param website
     * @return
     */
    int addWebsiteConfig(Website website);

    /**
     * 更新网站配置
     * @param website
     * @return
     */
    int updateWebsiteConfig(Website website);
}
