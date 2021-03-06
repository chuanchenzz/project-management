package com.outsource.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;
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
        Long size = redisTemplate.opsForZSet().size(key);
        return redisTemplate.opsForZSet().range(key, 0, size);
    }

    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    public Long removeZSetEntry(String key, Object value){
        return redisTemplate.opsForZSet().remove(key,value);
    }

    public Long zSetSize(String key){
        return redisTemplate.opsForZSet().size(key);
    }

    public List<Integer> rangeZSet(String key, int pageNumber, int pageSize){
        long offset = (pageNumber - 1) * pageSize;
        Set<Integer> set = redisTemplate.opsForZSet().reverseRange(key,offset,offset + pageSize - 1);
        return new ArrayList<>(set);
    }
}
