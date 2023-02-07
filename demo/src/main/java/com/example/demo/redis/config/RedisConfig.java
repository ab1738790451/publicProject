package com.example.demo.redis.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/11/30 17:01
 * @Version: 1.0.0
 * @Description: 描述
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port);
        if (StringUtils.isNotEmpty(password)) {
            config.useSingleServer().setPassword(password);
        }
        return Redisson.create(config);
    }

    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public RedisProperties redisProperties(){
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setDatabase(0);
        redisProperties.setHost(host);
        redisProperties.setPort(port);
        redisProperties.setPassword(password);
        redisProperties.setSsl(false);
        redisProperties.setTimeout(Duration.of(30, ChronoUnit.SECONDS));
        RedisProperties.Pool pool = new RedisProperties.Pool();
        pool.setMinIdle(1);
        pool.setMaxIdle(5);
        pool.setMaxActive(64);
        pool.setMaxWait(Duration.of(10,ChronoUnit.SECONDS));
        redisProperties.getLettuce().setPool(pool);
        return redisProperties;
    }
}

