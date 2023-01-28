package com.woshen.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 股票走势表
 * </p>
 *
 * @author liuhaibo
 * @since 2023-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StockTimeSharing implements Serializable {


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


}
