package com.woshen.stock.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.webcommon.db.service.impl.BaseServiceImpl;
import com.woshen.stock.entity.StockTimeSharing;
import com.woshen.stock.mapper.StockTimeSharingMapper;
import com.woshen.stock.server.IStockTimeSharingService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 股票走势表 服务实现类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Service
public class StockTimeSharingServiceImpl extends BaseServiceImpl<Integer, StockTimeSharingMapper, StockTimeSharing> implements IStockTimeSharingService {
    @Override
    public QueryWrapper<StockTimeSharing> getBaseWrapper(StockTimeSharing queryData) {
        QueryWrapper<StockTimeSharing> baseWrapper = super.getBaseWrapper(queryData);
        String createStartTime = (String)queryData.getQueryParam("createStartTime");
        if(StringUtils.isNotBlank(createStartTime)){
            baseWrapper.gt("date",createStartTime);
        }
        String createEndTime = (String)queryData.getQueryParam("createEndTime");
        if(StringUtils.isNotBlank(createEndTime)){
            baseWrapper.gt("date",createEndTime);
        }
        String name = queryData.getName();
        if(StringUtils.isNotBlank(name)){
            baseWrapper.apply("code in(select code from stock where name like concat('%','"+name+"','%'))");
        }
        return baseWrapper;
    }
}
