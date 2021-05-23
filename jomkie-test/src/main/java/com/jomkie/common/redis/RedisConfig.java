package com.jomkie.common.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean(name = "strRedisTemplate")
    public RedisTemplate<String, String> strRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> stringRedisTemplate = new RedisTemplate<>();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }

}
