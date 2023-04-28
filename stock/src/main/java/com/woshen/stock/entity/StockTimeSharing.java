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
 * 股票走势表
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StockTimeSharing extends BaseEntity<Integer> {


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
     * 总走势
     */
    @TableField("trends_total")
    private Integer trendsTotal;

    /**
     * 走势详情
     */
    private String trends;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 股票名称
     */
    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private BigDecimal lastClose;

    @TableField(exist = false)
    private BigDecimal open;

    @TableField(exist = false)
    private BigDecimal close;

    @TableField(exist = false)
    private BigDecimal high;

    @TableField(exist = false)
    private BigDecimal low;

    @TableField(exist = false)
    private BigDecimal priceChange;


    @Override
    public Integer getPk() {
    return this.id;
    }


}
