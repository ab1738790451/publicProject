package com.woshen.stock.server.impl;

import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.mapper.StockDayInformationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woshen.stock.server.IStockDayInformationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-01-27
 */
@Service
public class StockDayInformationServiceImpl extends ServiceImpl<StockDayInformationMapper, StockDayInformation> implements IStockDayInformationService {

}
