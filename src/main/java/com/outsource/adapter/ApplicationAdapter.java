package com.outsource.adapter;

import com.outsource.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author chuanchen
 */
@Configuration
public class ApplicationAdapter extends WebMvcConfigurerAdapter{
    @Autowired
    AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/admins/[0-9]+").addPathPatterns("/admins/");
        super.addInterceptors(registry);
    }
}
