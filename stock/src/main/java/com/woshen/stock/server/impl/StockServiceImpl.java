package com.woshen.stock.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.webcommon.db.service.impl.BaseServiceImpl;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.mapper.StockMapper;
import com.woshen.stock.server.IStockService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 股票表 服务实现类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Service
public class StockServiceImpl extends BaseServiceImpl<Integer, StockMapper, Stock> implements IStockService {

    @Override
    public QueryWrapper<Stock> getBaseWrapper(Stock queryData) {
        String name = queryData.getName();
        queryData.setName(null);
        QueryWrapper<Stock> baseWrapper = super.getBaseWrapper(queryData);
        if(StringUtils.isNotBlank(name)){
            baseWrapper.like("name",name);
            queryData.setName(name);
        }
        return baseWrapper;
    }
}
