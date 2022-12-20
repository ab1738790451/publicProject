package com.example.demo.redis.core;

import java.io.Serializable;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/19 18:08
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface RedisStringOperations{

    void set(RedisKeyNs key, Serializable id, String value);

    Boolean setIfAbsent(RedisKeyNs key, Serializable id, String value);


    Boolean setIfPresent(RedisKeyNs key, Serializable id, String value);


    String get(RedisKeyNs key, Serializable id);


    String getAndSet(RedisKeyNs key, Serializable id, String value);

    Long increment(RedisKeyNs key, Serializable id);


    Long increment(RedisKeyNs key, Serializable id, long delta);



    Double increment(RedisKeyNs key, Serializable id, double delta);


    Long decrement(RedisKeyNs key, Serializable id);


    Long decrement(RedisKeyNs key, Serializable id, long delta);


    Integer append(RedisKeyNs key, Serializable id, String value);

    Long len(RedisKeyNs key, Serializable id);

}
