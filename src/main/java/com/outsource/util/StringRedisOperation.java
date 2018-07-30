package com.outsource.util;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author chuanchen
 */
public class StringRedisOperation {
    private StringRedisTemplate stringRedisTemplate;

    public StringRedisOperation(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void setExpire(String key, String value, long expireTime) {
        stringRedisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }
}
