package com.woshen.stock.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.mapper.StockMapper;
import com.woshen.stock.server.IStockService;
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
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {

}
