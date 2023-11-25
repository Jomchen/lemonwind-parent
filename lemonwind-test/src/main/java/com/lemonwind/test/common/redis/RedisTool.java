package com.lemonwind.test.common.redis;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@Getter
@Setter
public class RedisTool {

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;

    public String get(String key) {
        return strRedisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value) {
        strRedisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeLength, TimeUnit timeUnit) {
        strRedisTemplate.opsForValue().set(key, value, timeLength, timeUnit);
    }

    /**
     * @author lemonwind
     * @since 2021-05-23 21:2:19
     * @param key 对象的 key
     * @param typeReference 转换引用
     * 查询一个对象
     */
    public <T> T getObj(String key, TypeReference<T> typeReference) {
        String valueJson = strRedisTemplate.opsForValue().get(key);
        T resultData = Optional.ofNullable(valueJson)
                .map(v -> JSONObject.parseObject(valueJson, typeReference))
                .orElse(null);
        return resultData;
    }

    /**
     * @author lemonwind
     * @since 2021-05-23 21:3:55
     * @param key  存储的 key
     * @param data 存储的对象
     */
    public <T> T setObj(String key, T data) {
        String valueJson = data instanceof String ? (String) data : JSONObject.toJSONString(data);
        strRedisTemplate.opsForValue().set(key, valueJson);
        return data;
    }

    /**
     * @author lemonwind
     * @since 2021-05-23 21:3:55
     * @param key  存储的 key
     * @param data 存储的对象
     * @param timeLength 时间长度
     * @param timeUnit 时间单位
     */
    public <T> void setObj(String key, T data, long timeLength, TimeUnit timeUnit) {
        String valueJson = data instanceof String ? (String) data : JSONObject.toJSONString(data);
        strRedisTemplate.opsForValue().set(key, valueJson, timeLength, timeUnit);
    }

    /**
     * @author lemonwind
     * @since 2021-05-24 23:44:54
     * @param key
     * 删除
     */
    public boolean delete(String key) {
        return strRedisTemplate.delete(key);
    }

}
