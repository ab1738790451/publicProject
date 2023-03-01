package com.woshen.common.beanModel;

/**
 * @Author: liuhaibo
 * @Date: 2023/3/1 17:40
 * @Version: 1.0.0
 * @Description: 描述
 */
public enum Bool {
    Y("是"),
    N("否"),
    U("未知");
    private String desc;

    private String value;

    Bool(String desc){
        this.desc = desc;
        this.value = this.name();
    }

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }
}
