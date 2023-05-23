package com.woshen.stock.vo;

import com.woshen.stock.constant.PriceChangeType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author: liuhaibo
 * @Date: 2023/5/23 17:30
 * @Version: 1.0.0
 * @Description: 描述
 */
@Data
public class StockDayInformationVO {

    private String code;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer total;

    private BigDecimal priceChange;

    private PriceChangeType priceChangeType;
}
