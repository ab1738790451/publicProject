package com.woshen.stock.server;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.webcommon.db.service.BaseService;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.vo.StockDayInformationVO;

/**
 * <p>
 * 股票每日数据表 服务类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
public interface IStockDayInformationService extends BaseService<Integer, StockDayInformation> {

    Page<StockDayInformationVO> selectLXZT(StockDayInformationVO stockDayInformationVO,Page<StockDayInformationVO> page);

    Page<StockDayInformationVO> selectLXDT(StockDayInformationVO stockDayInformationVO,Page<StockDayInformationVO> page);
}
