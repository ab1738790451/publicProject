package com.woshen.stock.constant;

/**
 * @Author: liuhaibo
 * @Date: 2023/5/19 10:30
 * @Version: 1.0.0
 * @Description: 描述
 */
public enum PriceChangeType {
    LIMIT_UP("涨停"),
    LIMIT_DOWN("跌停"),
    RISE("涨"),
    FALL("跌"),
    FLAT("平");
    PriceChangeType(String desc){
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
