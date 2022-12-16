package com.example.demo.utils;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/16 16:01
 * @Version: 1.0.0
 * @Description: 描述
 */
@Component
public class RedisUtil {

    private static RedisTemplate<String,String> redisTemplate;

    static {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + BaseConfigUtils.getProperty("spring.redis.host") + ":" + BaseConfigUtils.getProperty("spring.redis.port"))
                .setPassword(BaseConfigUtils.getProperty("spring.redis.password"))
                .setConnectionMinimumIdleSize(1)
                .setConnectionPoolSize(8);
        RedissonClient redissonClient = Redisson.create(config);
        RedisConnectionFactory factory = new RedissonConnectionFactory(redissonClient);
        redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
    }

    private RedisUtil(){}

    public static void set(String key,String vlaue){
        redisTemplate.opsForValue().set(key,vlaue,10, TimeUnit.SECONDS);
    }

    public static String get(String key){
     return  redisTemplate.opsForValue().get(key);
    }
}
