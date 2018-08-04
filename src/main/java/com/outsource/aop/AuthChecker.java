package com.outsource.aop;

import com.outsource.constant.StatusCodeEnum;
import com.outsource.interceptor.AuthEnum;
import com.outsource.model.AdminVO;
import com.outsource.model.JsonResponse;
import com.outsource.service.IAdminService;
import com.outsource.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chuanchen
 */
@Component
@Aspect
public class AuthChecker {
    private static final Logger logger = LoggerFactory.getLogger(AuthChecker.class);
    private static final String ADMIN_ID = "admin_id";
    @Autowired
    IAdminService adminService;

    @Around("@annotation(authLevel)")
    public Object authCheck(ProceedingJoinPoint joinPoint, AuthLevel authLevel) throws Throwable{
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return new JsonResponse<>(StatusCodeEnum.AUTH_ERROR.getCode(),"没有权限访问!");
        }
        HttpServletRequest request = (HttpServletRequest) args[0];
        String adminId = (String) request.getAttribute(ADMIN_ID);
        if(StringUtils.isEmpty(adminId)){
            return new JsonResponse<>(StatusCodeEnum.NOT_LOGIN.getCode(),"没有权限访问!");
        }
        AdminVO admin = adminService.findAdmin(Integer.valueOf(adminId));
        if(admin == null){
            return new JsonResponse<>(StatusCodeEnum.NOT_FOUND.getCode(),"admin not found!");
        }
        Integer level = admin.getLevel();
        AuthEnum authEnum = authLevel.type();
        switch (authEnum){
            case NORMAL:{
                break;
            }
            case ACCOUNT_MANAGR:{
                if((level & 2) != 2){
                    return new JsonResponse<>(StatusCodeEnum.AUTH_ERROR.getCode(),"没有权限访问!");
                }
                break;
            }
            case REVIEW_MANAGER:{
                if((level & 1) != 1){
                    return new JsonResponse<>(StatusCodeEnum.AUTH_ERROR.getCode(),"没有权限访问!");
                }
                break;
            }
            case SUPER_MANAGER:{
                if((level & 3) != 3){
                    return new JsonResponse<>(StatusCodeEnum.AUTH_ERROR.getCode(),"没有权限访问!");
                }
                break;
            }
            default:{
                return new JsonResponse<>(StatusCodeEnum.AUTH_ERROR.getCode(),"没有权限访问!");
            }
        }
        try {
            return joinPoint.proceed(args);
        } catch (Throwable throwable) {
            logger.error("error!",throwable);
            throw throwable;
        }
    }
}
