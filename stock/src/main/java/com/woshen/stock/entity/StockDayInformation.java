package com.woshen.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.woshen.common.webcommon.db.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 股票每日数据表
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StockDayInformation extends BaseEntity<Integer> {


    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票代码
     */
    private String code;

    /**
     * 交易日期
     */
    @TableField("transaction_date")
    private LocalDate transactionDate;

    /**
     * 昨日收盘价
     */
    private BigDecimal close;

    /**
     * 开盘价
     */
    private BigDecimal open;

    /**
     * 收盘价
     */
    @TableField("today_close")
    private BigDecimal todayClose;

    /**
     * 最高价
     */
    private BigDecimal high;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 平均价
     */
    private BigDecimal average;

    /**
     * 涨跌幅
     */
    @TableField("price_change")
    private BigDecimal priceChange;

    /**
     * 成交量
     */
    private Integer volume;

    /**
     * 成交额
     */
    private BigDecimal turnover;

    /**
     * 换手率
     */
    @TableField("turnover_rate")
    private BigDecimal turnoverRate;

    /**
     * 总市值
     */
    @TableField("total_worth")
    private BigDecimal totalWorth;

    /**
     * 流通市值
     */
    @TableField("circulation_worth")
    private BigDecimal circulationWorth;

    /**
     * 市净率
     */
    private BigDecimal pb;

    /**
     * 静态市盈率
     */
    private BigDecimal spe;

    /**
     * 市盈率
     */
    private BigDecimal pe;

    /**
     * 超大单流入
     */
    @TableField("super_inflow")
    private Long superInflow;

    /**
     * 超大单流入金额
     */
    @TableField(exist = false)
    private BigDecimal superInflowAmount;

    /**
     * 超大单流出
     */
    @TableField("super_outflow")
    private Long superOutflow;

    /**
     * 超大单流出金额
     */
    @TableField(exist = false)
    private BigDecimal superOutflowAmount;

    /**
     * 大单流入
     */
    @TableField("max_inflow")
    private Long maxInflow;

    /**
     * 大单流入金额
     */
    @TableField(exist = false)
    private BigDecimal maxInflowAmount;

    /**
     * 大单流出
     */
    @TableField("max_outflow")
    private Long maxOutflow;

    /**
     * 大单流出金额
     */
    @TableField(exist = false)
    private BigDecimal maxOutflowAmount;

    /**
     * 中单流入
     */
    @TableField("middle_inflow")
    private Long middleInflow;

    /**
     * 中单流入金额
     */
    @TableField(exist = false)
    private BigDecimal middleInflowAmount;

    /**
     * 中单流出
     */
    @TableField("middle_outflow")
    private Long middleOutflow;

    /**
     * 超大单流出金额
     */
    @TableField(exist = false)
    private BigDecimal middleOutflowAmount;

    /**
     * 小单流入
     */
    @TableField("min_inflow")
    private Long minInflow;

    /**
     * 小单流入金额
     */
    @TableField(exist = false)
    private BigDecimal minInflowAmount;

    /**
     * 小单流出
     */
    @TableField("min_outflow")
    private Long minOutflow;

    /**
     * 小单流出金额
     */
    @TableField(exist = false)
    private BigDecimal minOutflowAmount;

    /**
     * 总股本
     */
    @TableField("total_capital_stock")
    private Long totalCapitalStock;

    /**
     * 总营收
     */
    @TableField("total_revenue")
    private BigDecimal totalRevenue;

    /**
     * 流通股
     */
    @TableField("circulation_capital_stock")
    private Long circulationCapitalStock;

    /**
     * 毛利率
     */
    @TableField("interest_rate")
    private BigDecimal interestRate;

    /**
     * 净利率
     */
    @TableField("net_interest_rate")
    private BigDecimal netInterestRate;

    /**
     * 负债率
     */
    @TableField("debt_ratio")
    private BigDecimal debtRatio;

    /**
     * 每股未分配利润
     */
    private BigDecimal bonus;

    /**
     * 图解
     */
    private String graphic;

    /**
     * 股票名称
     */
    @TableField(exist = false)
    private String name;

    /**
     * 主力流入
     */
    @TableField(exist = false)
    private Long mainInflow;

    /**
     * 主力流入金额
     */
    @TableField(exist = false)
    private BigDecimal mainInflowAmount;

    /**
     * 主力流出
     */
    @TableField(exist = false)
    private Long mainOutflow;

    /**
     * 主力流出金额
     */
    @TableField(exist = false)
    private BigDecimal mainOutflowAmount;

    /**
     * 散户流入
     */
    @TableField(exist = false)
    private Long subInflow;

    /**
     * 散户流入金额
     */
    @TableField(exist = false)
    private BigDecimal subInflowAmount;
    /**
     * 散户流出
     */
    @TableField(exist = false)
    private Long subOutflow;

    /**
     * 散户流出金额
     */
    @TableField(exist = false)
    private BigDecimal subOutflowAmount;

    @Override
    public Integer getPk() {
    return this.id;
    }


}
