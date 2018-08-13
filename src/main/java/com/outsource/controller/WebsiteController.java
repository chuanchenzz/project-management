package com.outsource.controller;

import com.outsource.constant.StatusCodeEnum;
import com.outsource.model.JsonResponse;
import com.outsource.model.Website;
import com.outsource.service.IWebsiteService;
import com.outsource.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chuanchen
 */
@RestController
@RequestMapping("/website")
public class WebsiteController {
    @Autowired
    IWebsiteService websiteService;
    @RequestMapping(value = "/config",method = RequestMethod.POST)
    public JsonResponse<Website> addWebsiteConfig(@RequestParam("name") String name, @RequestParam("fav_icon") String favIcon,@RequestParam("logo") String logo){
        if(StringUtils.isEmpty(name,favIcon,logo)){
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"参数错误!");
        }
        Website website = websiteService.updateOrAddWebsiteConfigIfAbsent(name,favIcon,logo);
        return website == null ? new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(),"内部错误!") : new JsonResponse<>(website,StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/config",method = RequestMethod.GET)
    public JsonResponse<Website> findWebsiteConfig(){
        Website website = websiteService.findWebsiteConfig();
        return website == null ? new JsonResponse<>(StatusCodeEnum.NOT_FOUND.getCode(),"未找到!") : new JsonResponse<>(website,StatusCodeEnum.SUCCESS.getCode());
    }
}
