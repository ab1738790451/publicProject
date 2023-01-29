package com.woshen.stock.xxljob;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.launchdarkly.eventsource.EventSource;
import com.woshen.stock.core.EventSourceFactory;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.entity.StockTimeSharing;
import com.woshen.stock.handler.StockDetailsHandler;
import com.woshen.stock.handler.StockTimeSharingHandler;
import com.woshen.stock.server.IStockDayInformationService;
import com.woshen.stock.server.IStockService;
import com.woshen.stock.server.IStockTimeSharingService;
import com.woshen.stock.utils.TaskUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/1/28 20:25
*@version 1.0
*@description
*/
@Component
public class StockDayImformationJob {
    @Autowired
    private IStockService stockServiceImpl;
    @Autowired
    private IStockTimeSharingService stockTimeSharingServiceImpl;
    @Autowired
    private IStockDayInformationService stockDayInformationServiceImpl;

    @XxlJob("stockDayImformationJob")
    public void exec(String... params) throws InterruptedException {
        execOnePage(1,5);
    }

    private void execOnePage(Integer pageIndex,Integer pageSize) throws InterruptedException {
        Page<Stock> stockPage = new Page<>(pageIndex,pageSize);
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","GP");
        IPage<Stock> page = stockServiceImpl.getBaseMapper().selectPage(stockPage, queryWrapper);
        List<Stock> records = page.getRecords();
        if(CollectionUtils.isEmpty(records)){
            return;
        }
        List<String> codes = records.stream().map(Stock::getCode).collect(Collectors.toList());
        QueryWrapper<StockTimeSharing> stockTimeSharingQueryWrapper = new QueryWrapper<>();
        stockTimeSharingQueryWrapper.in("code",codes);
        stockTimeSharingQueryWrapper.orderByDesc("date");
        stockTimeSharingQueryWrapper.last("limit " + codes.size());
        List<StockTimeSharing> list = stockTimeSharingServiceImpl.list(stockTimeSharingQueryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            LocalDate date = list.get(0).getDate();
            List<String> queryCodes = list.stream().filter(t -> date.equals(t.getDate())).map(StockTimeSharing::getCode).collect(Collectors.toList());
            QueryWrapper<StockDayInformation> stockDayInformationQueryWrapper = new QueryWrapper<>();
            stockDayInformationQueryWrapper.in("code",queryCodes);
            stockDayInformationQueryWrapper.eq("transaction_date",date);
            List<StockDayInformation> stockDayInformations = stockDayInformationServiceImpl.list(stockDayInformationQueryWrapper);
            List<String> excludeCodes = stockDayInformations.stream().map(StockDayInformation::getCode).collect(Collectors.toList());
            queryCodes.stream().forEach(t -> {
                if(!excludeCodes.contains(t)){
                    asyncLoad(t);
                }
            });
            Thread.sleep(20000);
        }
        if(page.getPages() > pageIndex ){
            execOnePage(pageIndex +1,pageSize);
        }
    }

    private void asyncLoad(String code){
        TaskUtil.exec(()->{
            EventSource eventSource = EventSourceFactory.createEventSource(code,new StockDetailsHandler());
            eventSource.start();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventSource.close();
        });
    }

}
