package com.woshen.acl.constants;

/**
 * @author liuhaibo
 * @version 1.0
 * @company woshen
 * @Date 2023/2/15 0:34
 * @description
 */
public enum UserType {
    ORDINARY("普通用户"),
    ADMIN("管理员"),
    SUPER_ADMIN("超级管理员");
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
