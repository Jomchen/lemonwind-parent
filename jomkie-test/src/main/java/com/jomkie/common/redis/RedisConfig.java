package com.jomkie.common.redis;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

@Configuration
public class RedisConfig {

    @Bean(name = "strRedisTemplate")
    public RedisTemplate<String, String> strRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 存储后，值不能在库里正常显示
        RedisTemplate<String, String> stringRedisTemplate = new RedisTemplate<>();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);

        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonRedisSerializer.setFastJsonConfig(fastJsonConfig);
        stringRedisTemplate.setDefaultSerializer(fastJsonRedisSerializer);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer(StandardCharsets.UTF_8);
        stringRedisTemplate.setStringSerializer(stringRedisSerializer);
        stringRedisTemplate.setKeySerializer(stringRedisSerializer);
        stringRedisTemplate.setValueSerializer(stringRedisSerializer);
        return stringRedisTemplate;
    }

}
