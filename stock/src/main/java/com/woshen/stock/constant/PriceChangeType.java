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
    RISE("上涨(不含涨停)"),
    FALL("跌(不含跌停)"),
    FLAT("平"),
    RISE_ALL("上涨含涨停"),
    FALL_ALL("下跌含跌停");
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
