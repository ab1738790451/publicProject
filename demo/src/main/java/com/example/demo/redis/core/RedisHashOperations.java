package com.example.demo.redis.core;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/20 14:04
 * @Version: 1.0.0
 * @Description: redis哈希操作
 */
public interface RedisHashOperations  {

    Long delete(RedisKeyNs key, Serializable id, String... hashKeys);

    Boolean hasKey(RedisKeyNs key, Serializable id, String hashKey);

    String get(RedisKeyNs key, Serializable id, String hashKey);

    /**
     * 从{@key}中获取多个hash key的值
     * @param key
     * @param id
     * @param hashKeys
     * @return
     */
    List<String> multiGet(RedisKeyNs key, Serializable id, Collection<String> hashKeys);

    Long increment(RedisKeyNs key, Serializable id, String hashKey, long delta);

    Double increment(RedisKeyNs key, Serializable id, String hashKey, double delta);

    Set<String> keys(RedisKeyNs key, Serializable id);

    /**
     * 获取单个hashkey的值长度
     * @param key
     * @param id
     * @param hashKey
     * @return
     */
    Long lengthOfValue(RedisKeyNs key, Serializable id, String hashKey);

    Long size(RedisKeyNs key, Serializable id);

    void putAll(RedisKeyNs key, Serializable id, Map<? extends String, ? extends String> m);

    void put(RedisKeyNs key, Serializable id, String hashKey, String value);

    Boolean putIfAbsent(RedisKeyNs key, Serializable id, String hashKey, String value);

    List<String> values(RedisKeyNs key, Serializable id);

    /**
     * 获取{@key}所有的值
     * @param key
     * @param id
     * @return
     */
    Map<String, String> entries(RedisKeyNs key, Serializable id);

}
