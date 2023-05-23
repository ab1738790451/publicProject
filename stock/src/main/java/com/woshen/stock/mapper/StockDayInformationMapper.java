package com.woshen.stock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.vo.StockDayInformationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    Page<StockDayInformationVO> selectLXZT(@Param("param")StockDayInformationVO stockDayInformationVO);

    Page<StockDayInformationVO> selectLXDT(@Param("param")StockDayInformationVO stockDayInformationVO);

}
