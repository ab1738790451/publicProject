package com.example.demo.redis.core;

import java.io.Serializable;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/19 18:08
 * @Version: 1.0.0
 * @Description: redis 字符串操作
 */
public interface RedisStringOperations{

    void set(RedisKeyNs key, Serializable id, String value);

    /**
     * 如果不存在就设置
     * @param key
     * @param id
     * @param value
     * @return
     */
    Boolean setIfAbsent(RedisKeyNs key, Serializable id, String value);

    /**
     * 如果存在就设置
     * @param key
     * @param id
     * @param value
     * @return
     */
    Boolean setIfPresent(RedisKeyNs key, Serializable id, String value);


    String get(RedisKeyNs key, Serializable id);

    /**
     * 设置新值并返回原来的旧值
     * @param key
     * @param id
     * @param value
     * @return
     */
    String getAndSet(RedisKeyNs key, Serializable id, String value);

    /**
     * 数值自增，默认增量为1
     * @param key
     * @param id
     * @return
     */
    Long increment(RedisKeyNs key, Serializable id);

    /**
     * 数值增加{@delta}
     * @param key
     * @param id
     * @param delta
     * @return
     */
    Long increment(RedisKeyNs key, Serializable id, long delta);


    /**
     * 数值增加{@delta}
     * @param key
     * @param id
     * @param delta
     * @return
     */
    Double increment(RedisKeyNs key, Serializable id, double delta);

    /**
     * 数值自减，默认减量为1
     * @param key
     * @param id
     * @return
     */
    Long decrement(RedisKeyNs key, Serializable id);

    /**
     * 数值减少{@delta}
     * @param key
     * @param id
     * @param delta
     * @return
     */
    Long decrement(RedisKeyNs key, Serializable id, long delta);

    /**
     * 在原值后面附加{@value}
     * @param key
     * @param id
     * @param value
     * @return
     */
    Integer append(RedisKeyNs key, Serializable id, String value);

    /**
     * 获取字符串的长度
     * @param key
     * @param id
     * @return
     */
    Long len(RedisKeyNs key, Serializable id);

}
