package com.outsource.service;

import com.outsource.model.Website;

/**
 * @author chuanchen
 */
public interface IWebsiteService {
    /**
     * 更新或者当不存在时添加网站配置
     * @param name
     * @param favIcon
     * @param logo
     * @return
     */
    Website updateOrAddWebsiteConfigIfAbsent(String name,String favIcon,String logo);

    /**
     * 获取网站配置信息
     * @return
     */
    Website findWebsiteConfig();
}
