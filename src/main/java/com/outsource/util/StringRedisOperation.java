package com.outsource.util;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author chuanchen
 */
public class StringRedisOperation {
    private StringRedisTemplate stringRedisTemplate;

    public StringRedisOperation(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
