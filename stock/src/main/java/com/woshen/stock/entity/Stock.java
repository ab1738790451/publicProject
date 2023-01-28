package com.woshen.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuhaibo
 * @since 2023-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Stock implements Serializable {


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
     * 股票名称
     */
    private String name;

    /**
     * 股票类型
     */
    private String type;

    /**
     * 历史最高价
     */
    @TableField("his_high")
    private BigDecimal hisHigh;

    /**
     * 历史最低价
     */
    @TableField("his_low")
    private BigDecimal hisLow;

    /**
     * 上市日期
     */
    @TableField("sh_date")
    private LocalDate shDate;

    /**
     * 行业
     */
    private String industry;

    /**
     * 板块
     */
    private String plate;


}
