package com.example.demo.redis.core;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/21 16:54
 * @Version: 1.0.0
 * @Description: zset模型
 */
public class ZSetDataModel {

    private String value;

    private Double core;

    public ZSetDataModel(String value,Double core){
        this.value = value;
        this.core = core;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getCore() {
        return core;
    }

    public void setCore(Double core) {
        this.core = core;
    }
}
