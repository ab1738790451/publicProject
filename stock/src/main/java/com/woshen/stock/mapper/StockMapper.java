package com.woshen.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woshen.stock.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 股票表 Mapper 接口
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

}
