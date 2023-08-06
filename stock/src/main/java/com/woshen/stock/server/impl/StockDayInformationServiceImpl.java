package com.woshen.stock.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.webcommon.db.service.impl.BaseServiceImpl;
import com.woshen.common.webcommon.model.PageInfo;
import com.woshen.stock.constant.PriceChangeType;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.mapper.StockDayInformationMapper;
import com.woshen.stock.server.IStockDayInformationService;
import com.woshen.stock.vo.StockDayInformationVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 股票每日数据表 服务实现类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Service
public class StockDayInformationServiceImpl extends BaseServiceImpl<Integer, StockDayInformationMapper, StockDayInformation> implements IStockDayInformationService {

    @Override
    public QueryWrapper<StockDayInformation> getBaseWrapper(StockDayInformation queryData) {
        QueryWrapper<StockDayInformation> baseWrapper = super.getBaseWrapper(queryData);
        String createStartTime = (String)queryData.getQueryParam("createStartTime");
        if(StringUtils.isNotBlank(createStartTime)){
            baseWrapper.ge("transaction_date",createStartTime);
        }
        String createEndTime = (String)queryData.getQueryParam("createEndTime");
        if(StringUtils.isNotBlank(createEndTime)){
            baseWrapper.le("transaction_date",createEndTime);
        }
        String name = queryData.getName();
        if(StringUtils.isNotBlank(name)){
            baseWrapper.apply("code in(select code from stock where name like concat('%','"+name+"','%'))");
        }
        return baseWrapper;
    }


    @Override
    public Page<StockDayInformationVO> selectLXZT(StockDayInformationVO stockDayInformationVO){
        if(stockDayInformationVO.getPriceChange() != null && stockDayInformationVO.getPriceChange().doubleValue() == 0){
            stockDayInformationVO.setPriceChange(null);
            stockDayInformationVO.setChangeType(PriceChangeType.FLAT);
        }
        PageInfo pageInfo = stockDayInformationVO.getPageInfo();
        if(pageInfo.getPageSize() > 100){
            pageInfo.setPageSize(10);
        }
        Page<StockDayInformationVO> page = new Page<>(pageInfo.getPageIndex(),pageInfo.getPageSize());
       return this.getBaseMapper().selectLXZT(page,stockDayInformationVO);
    }
}
