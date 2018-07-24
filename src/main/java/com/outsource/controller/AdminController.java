package com.outsource.controller;

import com.outsource.constant.StatusCodeEnum;
import com.outsource.model.JsonResponse;
import com.outsource.service.IAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author chuanchen
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    IAdminService adminService;

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResponse<Integer> updateAdmin(@PathVariable("id") int id, @RequestParam("password") String password, @RequestParam("level") Integer level) {
        boolean isValidParams = (id > 0 && !StringUtils.isEmpty(password) && level > 0);
        if (!isValidParams) {
            return new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(), "参数错误!");
        }
        Integer adminId = adminService.updateAdmin(id, password, level);
        if (adminId == null) {
            return new JsonResponse<>(StatusCodeEnum.SERVER_ERROR.getCode(), "内部错误!");
        }
        return new JsonResponse<>(StatusCodeEnum.SUCCESS.getCode(), adminId);
    }
}
