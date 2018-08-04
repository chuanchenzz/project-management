package com.outsource.aop;

import com.outsource.interceptor.AuthEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface AuthLevel {
    AuthEnum type() default AuthEnum.NORMAL;
}
