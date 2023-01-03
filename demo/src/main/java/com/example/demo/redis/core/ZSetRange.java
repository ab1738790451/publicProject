package com.example.demo.redis.core;

import org.springframework.data.redis.connection.RedisZSetCommands;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/21 17:33
 * @Version: 1.0.0
 * @Description: zset截取模型
 */
public class ZSetRange {

    private Double min;

    private Double max;

    private RedisZSetCommands.Range range;

    public ZSetRange(Double min,Double max){
        this.min = min;
        this.max = max;
        setRange();
    }

    private void setRange(){
         range = new RedisZSetCommands.Range();
         range.gte(min);
         range.lte(max);
    }

    public RedisZSetCommands.Range getRange() {
        return range;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

}
