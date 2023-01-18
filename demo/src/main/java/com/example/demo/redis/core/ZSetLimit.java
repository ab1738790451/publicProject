package com.example.demo.redis.core;

import org.springframework.data.redis.connection.RedisZSetCommands;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/21 17:46
 * @Version: 1.0.0
 * @Description: zset分片模型
 */
public class ZSetLimit {

    private int offset;
    private int count;
    private RedisZSetCommands.Limit limit;

    public ZSetLimit(int offset,int count){
        this.offset = offset;
        this.count = count;
        setLimit();
    }

    private void setLimit(){
        RedisZSetCommands.Limit limit = new RedisZSetCommands.Limit();
        limit.offset(offset);
        limit.count(count);
    }

    public RedisZSetCommands.Limit getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }
}
