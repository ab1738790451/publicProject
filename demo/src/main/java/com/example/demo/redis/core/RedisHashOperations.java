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
 * @Description: 描述
 */
public interface RedisHashOperations  {

    Long delete(RedisKeyNs key, Serializable id, String... hashKeys);

    Boolean hasKey(RedisKeyNs key, Serializable id, String hashKey);

    String get(RedisKeyNs key, Serializable id, String hashKey);

    List<String> multiGet(RedisKeyNs key, Serializable id, Collection<String> hashKeys);

    Long increment(RedisKeyNs key, Serializable id, String hashKey, long delta);

    Double increment(RedisKeyNs key, Serializable id, String hashKey, double delta);

    Set<String> keys(RedisKeyNs key, Serializable id);

    Long lengthOfValue(RedisKeyNs key, Serializable id, String hashKey);

    Long size(RedisKeyNs key, Serializable id);

    void putAll(RedisKeyNs key, Serializable id, Map<? extends String, ? extends String> m);

    void put(RedisKeyNs key, Serializable id, String hashKey, String value);

    Boolean putIfAbsent(RedisKeyNs key, Serializable id, String hashKey, String value);

    List<String> values(RedisKeyNs key, Serializable id);

    Map<String, String> entries(RedisKeyNs key, Serializable id);

}
