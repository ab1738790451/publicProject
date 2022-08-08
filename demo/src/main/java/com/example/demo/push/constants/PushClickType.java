package com.example.demo.push.constants;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/7/29 14:02
 * @Version: 1.0.0
 * @Description: 消息通知的后续动作;
 * 注意：当用厂商通道推送时仅android厂商生效，并且其中payload 和  payload_custom 也不生效;
 * 默认为none
 */
public enum PushClickType {
    intent("打开应用内特定页面"),
    url("打开网页地址"),
    startapp("打开应用首页"),
    payload("自定义消息内容启动应用"),
    payload_custom("自定义消息内容不启动应用"),
    none("纯通知，无后续动作");

    PushClickType(String desc){
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
