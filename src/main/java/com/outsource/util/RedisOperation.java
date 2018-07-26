package com.outsource.util;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author chuanchen
 */
public class RedisOperation {
    private RedisTemplate redisTemplate;

    public RedisOperation(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
