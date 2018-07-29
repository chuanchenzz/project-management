package com.outsource.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author chuanchen
 */
public class RedisOperation {
    private RedisTemplate redisTemplate;

    public RedisOperation(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void addZsetItem(String key, Object value, double score){
        redisTemplate.opsForZSet().add(key,value,score);
    }
}
