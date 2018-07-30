package com.outsource.adapter;

import com.outsource.interceptor.AuthInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author chuanchen
 */
public class ApplicationAdapter extends WebMvcConfigurerAdapter{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/admins/**");
        super.addInterceptors(registry);
    }
}
