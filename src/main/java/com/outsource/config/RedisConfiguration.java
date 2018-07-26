package com.outsource.config;

import com.outsource.util.RedisOperation;
import com.outsource.util.StringRedisOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author chuanchen
 */
@Configuration
public class RedisConfiguration {

    @Bean
    RedisOperation redisOperation(RedisTemplate redisTemplate){
        return new RedisOperation(redisTemplate);
    }

    @Bean
    StringRedisOperation stringRedisOperation(StringRedisTemplate stringRedisTemplate){
        return new StringRedisOperation(stringRedisTemplate);
    }
}
