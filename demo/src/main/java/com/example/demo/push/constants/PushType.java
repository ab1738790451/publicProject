package com.example.demo.push.constants;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/7/28 14:11
 * @Version: 1.0.0
 * @Description: 描述
 */
public enum PushType {
    SINGLE("单人"),
    LIST("群体"),
    MESSAGE("创建消息"),
    ALL("全部人");

    PushType(String desc){
        this.desc = desc;
        this.value = this.name();
    }
    private String desc;

    private String value;

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }
}
