package com.woshen.stock.entity;

import lombok.Data;

import java.util.List;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/1/18 23:19
*@version 1.0
*@description
*/
@Data
public class DfcfStockTimeSharding {
    /**
     * 股票代码
     */
    private String code;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 总趋势
     */
    private Integer trendsTotal;
    /**
     * 趋势
     */
    private List<String> trends;
}
