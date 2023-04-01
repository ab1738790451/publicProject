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
 * 股票表
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Stock extends BaseEntity<Integer> {


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

    @Override
    public Integer getPk() {
    return this.id;
    }


}
