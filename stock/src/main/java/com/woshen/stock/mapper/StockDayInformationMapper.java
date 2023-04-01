package com.woshen.stock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woshen.stock.entity.StockDayInformation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 股票每日数据表 Mapper 接口
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Mapper
public interface StockDayInformationMapper extends BaseMapper<StockDayInformation> {

}
