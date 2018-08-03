package com.outsource.controller;

import com.outsource.constant.StatusCodeEnum;
import com.outsource.model.AdminVO;
import com.outsource.model.JsonResponse;
import com.outsource.service.IAdminService;
import com.outsource.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chuanchen
 */
@RestController
@RequestMapping("/admins")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    IAdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse<String> login(@RequestParam("account") String account, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(account, password)) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        String jsonSession = adminService.login(account, password);
        if (StringUtils.isEmpty(jsonSession)) {
            return new JsonResponse<>(StatusCodeEnum.SUCCESS.getCode(), "用户名或密码错误!");
        }
        return new JsonResponse<>(jsonSession, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public JsonResponse<Integer> updateAdmin(@PathVariable("id") int id, @RequestParam("password") String password, @RequestParam("level") Integer level) {
        boolean isValidParams = (id > 0 && !StringUtils.isEmpty(password) && level > 0);
        if (!isValidParams) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        Integer adminId = adminService.updateAdmin(id, password, level);
        if (adminId == null) {
            return new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!");
        }
        return new JsonResponse<>(adminId,StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponse<AdminVO> addAdmin(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("level") byte level) {
        boolean isValidParams = StringUtils.isNotEmpty(account, password) && level > 0;
        if (!isValidParams) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        AdminVO adminVO = adminService.findAdmin(account);
        if (adminVO != null) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "该用户名已经存在!");
        }
        AdminVO newAdmin = adminService.addAdmin(account, password, level);
        if (newAdmin == null) {
            return new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "添加用户失败!");
        }
        return new JsonResponse<>(newAdmin, StatusCodeEnum.SUCCESS.getCode());
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JsonResponse<List<AdminVO>> findAdminList() {
        List<AdminVO> adminList = adminService.findAdminList();
        if (adminList == null) {
            return new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!");
        }
        return new JsonResponse<>(adminList, StatusCodeEnum.SUCCESS.getCode());
    }

}
