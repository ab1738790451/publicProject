package com.woshen.acl.constants;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/12 11:13
 * @Version: 1.0.0
 * @Description: 描述
 */
public enum DataStatus {
    NORMAL("正常"),
    DELETED("删除");
    DataStatus(String descp){
        this.value = this.name();
        this.descp = descp;
    }
    private String value;

    private String descp;
}
