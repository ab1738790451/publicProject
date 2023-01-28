package com.woshen.stock.core;

import lombok.Data;

import java.util.List;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/1/18 23:19
*@version 1.0
*@description 股票趋势
*/
@Data
public class DfcfStockTimeShardingModel {
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
    /**
     * 日期
     */
    private String hisPrePrices;
}
