package com.woshen.stock.server.impl;

import com.woshen.stock.entity.StockTimeSharing;
import com.woshen.stock.mapper.StockTimeSharingMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woshen.stock.server.IStockTimeSharingService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 股票走势表 服务实现类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-01-27
 */
@Service
public class StockTimeSharingServiceImpl extends ServiceImpl<StockTimeSharingMapper, StockTimeSharing> implements IStockTimeSharingService {

}
