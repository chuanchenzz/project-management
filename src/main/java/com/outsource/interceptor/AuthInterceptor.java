package com.outsource.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outsource.constant.StatusCodeEnum;
import com.outsource.model.JsonResponse;
import com.outsource.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chuanchen
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private static final String ADMIN_SESSION = "admin_session";
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            printError(httpServletResponse);
            return false;
        }
        for (Cookie cookie : cookies) {
            boolean isLogin = ADMIN_SESSION.equals(cookie.getName()) && StringUtils.isNotEmpty(cookie.getValue());
            if (isLogin) {
                return true;
            }
        }
        printError(httpServletResponse);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void printError(HttpServletResponse httpServletResponse){
            PrintWriter pw = null;
            try {
                httpServletResponse.reset();
                httpServletResponse.setContentType("text/html;charset=utf8");
                pw = httpServletResponse.getWriter();
                pw.print(objectMapper.writeValueAsString(new JsonResponse<>(StatusCodeEnum.PARAMETER_ERROR.getCode(),"请先登陆!")));
                pw.flush();
            } catch (IOException e) {
                logger.error("get writer error!", e);
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
    }
}
