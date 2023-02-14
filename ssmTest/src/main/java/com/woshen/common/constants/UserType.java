package com.woshen.common.constants;

/**
 * @author liuhaibo
 * @version 1.0
 * @company woshen
 * @Date 2023/2/15 0:34
 * @description
 */
public enum UserType {
    ;
    UserType(String desc){
        this.desc = desc;
        this.value = this.name();
    }
    private String value;

    private String desc;

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
