package com.example.demo.redis.core;

import com.example.demo.redis.constants.RedisKeyType;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/20 11:05
 * @Version: 1.0.0
 * @Description: redis链表操作
 */
public interface RedisListOperations {

     /**
      * 从列表中获取{@code begin}和{@code end}之间的元素
      * @param key
      * @param id
      * @param start
      * @param end
      * @return
      */
     List<String> range(RedisKeyNs key, Serializable id, long start, long end);

     /**
      * 修剪列表到{@code start}和{@code end}之间的元素
      * @param key
      * @param id
      * @param start
      * @param end
      */
     void trim(RedisKeyNs key, Serializable id, long start, long end);

     Long size(RedisKeyNs key, Serializable id);

     Long lpush(RedisKeyNs key, Serializable id,String... values);

     Long leftPushAll(RedisKeyNs key, Serializable id, Collection<String> values);

     /**
      * 如果{@key}存在,从头部添加
      * @param key
      * @param id
      * @param value
      * @return
      */
     Long lPushIfPresent(RedisKeyNs key, Serializable id, String value);

     Long rPush(RedisKeyNs key, Serializable id, String... values);

     Long rPushAll(RedisKeyNs key, Serializable id, Collection<String> values);
     /**
      * 如果{@key}存在,从尾部添加
      * @param key
      * @param id
      * @param value
      * @return
      */
     Long rPushIfPresent(RedisKeyNs key, Serializable id, String value);

     void set(RedisKeyNs key, Serializable id, long index, String value);

     Long remove(RedisKeyNs key, Serializable id, long count, String value);

     String index(RedisKeyNs key, Serializable id, long index);

     String lPop(RedisKeyNs key, Serializable id);

     String rPop(RedisKeyNs key, Serializable id);

}
