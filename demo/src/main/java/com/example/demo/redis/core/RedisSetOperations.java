package com.example.demo.redis.core;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/20 10:53
 * @Version: 1.0.0
 * @Description: redis集合操作
 */
public interface RedisSetOperations {


     Long sadd(RedisKeyNs key, Serializable id, String... values);



     Long remove(RedisKeyNs key, Serializable id, Object... values);


     String pop(RedisKeyNs key, Serializable id);



     Set<String> pop(RedisKeyNs key, Serializable id, long count);



     Boolean move(RedisKeyNs key, Serializable id, String value, RedisKeyNs destKey, Serializable destId);



     Long ssize(RedisKeyNs key, Serializable id);



     Boolean isMember(RedisKeyNs key, Serializable id, String value);


     Set<String> members(RedisKeyNs key, Serializable id);

}
