package com.outsource.interceptor;

import com.outsource.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chuanchen
 */
public class AuthInterceptor implements HandlerInterceptor {
    private static final String ADMIN_SESSION = "admin_session";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            return false;
        }
        for (Cookie cookie : cookies) {
            boolean isLogin = ADMIN_SESSION.equals(cookie.getName()) && StringUtils.isNotEmpty(cookie.getValue());
            if (isLogin) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
