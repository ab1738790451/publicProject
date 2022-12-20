package com.example.demo.redis.constants;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/19 15:45
 * @Version: 1.0.0
 * @Description: 描述
 */
public enum RedisKeyType {
    LIST("链表"),
    SET("集合"),
    STRING("字符串"),
    HASH("哈希");

    RedisKeyType(String descr){
        this.descr = descr;
        this.value = this.name();
    }
    private String descr;

    private String value;
}
