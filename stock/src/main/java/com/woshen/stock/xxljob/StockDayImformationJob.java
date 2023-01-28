package com.woshen.stock.xxljob;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.launchdarkly.eventsource.EventSource;
import com.woshen.stock.core.EventSourceFactory;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.handler.StockDetailsHandler;
import com.woshen.stock.handler.StockTimeSharingHandler;
import com.woshen.stock.server.IStockService;
import com.woshen.stock.server.IStockTimeSharingService;
import com.woshen.stock.utils.TaskUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        records.stream().forEach(t -> {
            asyncLoad(t.getCode());
        });
        Thread.sleep(20000);
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
