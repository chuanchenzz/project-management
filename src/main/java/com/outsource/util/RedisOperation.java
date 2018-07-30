package com.outsource.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

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

    public void addZSetItem(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Integer> getZSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, redisTemplate.opsForSet().size(key) - 1);
    }

    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
}
