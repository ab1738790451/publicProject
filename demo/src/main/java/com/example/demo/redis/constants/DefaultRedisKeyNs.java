package com.example.demo.redis.constants;

import com.example.demo.redis.core.RedisKeyNs;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/20 10:08
 * @Version: 1.0.0
 * @Description: 描述
 */
public enum  DefaultRedisKeyNs implements RedisKeyNs {
    DEFAULT_LOCK_KEY(20,RedisKeyType.STRING,"默认的3秒锁");
    DefaultRedisKeyNs(long expire,RedisKeyType keyType,String descr){
        this.expire = expire;
        this.nameSpace = this.name();
        this.keyType = keyType;
        this.descr = descr;
    }

    private long expire;

    private String nameSpace;

    private RedisKeyType keyType;

    private String descr;

    @Override
    public long getExpire() {
        return expire;
    }

    @Override
    public String getNameSpace() {
        return nameSpace;
    }

    @Override
    public RedisKeyType getKeyType() {
        return keyType;
    }

    public String getDescr() {
        return descr;
    }
}
