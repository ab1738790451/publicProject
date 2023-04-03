package com.woshen.stock.constant;

/**
 * @Author: liuhaibo
 * @Date: 2023/4/3 15:35
 * @Version: 1.0.0
 * @Description: 描述
 */
public enum MainSubRate {
    NIE_EIGHT_TWO_ONE("主九八二一算法"),
    TWO_EIGHT("主八二算法");

    MainSubRate(String desc){
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
