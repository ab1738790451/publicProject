package com.example.demo.redis.core;

import com.example.demo.redis.constants.RedisKeyType;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/19 16:15
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface RedisKeyNs {

   long getExpire();

   String getNameSpace();

   RedisKeyType getKeyType();
}
