package com.woshen.stock.controller;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.launchdarkly.eventsource.EventSource;
import com.woshen.common.config.SpringUtils;
import com.woshen.stock.core.EventSourceFactory;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.handler.StockDetailsHandler;
import com.woshen.stock.handler.StockTimeSharingHandler;
import com.woshen.stock.handler.StockZixuanHandler;
import com.woshen.stock.server.MenuService;
import com.woshen.stock.server.impl.StockTimeSharingServiceImpl;
import com.woshen.stock.utils.StockUtils;
import com.woshen.stock.xxljob.StockDayImformationJob;
import com.woshen.stock.xxljob.StockRedJob;
import com.woshen.stock.xxljob.StockTrendsJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/1/13 15:10
 * @Version: 1.0.0
 * @Description: 描述
 */
@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private MenuService menuServiceImpl;

    @Autowired
    private StockTrendsJob stockTrendsJob;

    @Autowired
    private StockDayImformationJob stockDayImformationJob;

    @Autowired
    private StockRedJob stockRedJob;

    @RequestMapping("loadMenus")
    @ResponseBody
    public Map<String,Object> loadMenus(){
        Map<String,Object> map = new HashMap<>();
        map.put("data",menuServiceImpl.selectAll());
        return map;
    }


    @RequestMapping("stockTrend")
    @ResponseBody
    public String stockTrend() throws InterruptedException {
        stockTrendsJob.exec();
        return "连接成功";
    }

    @RequestMapping("closeStock")
    @ResponseBody
    public String closeStock() throws InterruptedException {
        stockDayImformationJob.exec();
        return "断开成功";
    }

    @RequestMapping("loadzixuan")
    @ResponseBody
    public String loadzixuan(String secids) throws InterruptedException {
        EventSource eventSource = EventSourceFactory.createEventSource(secids, new StockZixuanHandler());
        eventSource.start();
        Thread.sleep(10000);
        eventSource.close();
        return "加载自选";
    }

    @RequestMapping("stockRedJob")
    @ResponseBody
    public String stockRedJob() throws IOException, InterruptedException {
        stockRedJob.exec();
        return "加载自选";
    }


    @RequestMapping("loadStock")
    @ResponseBody
    public String loadStock(String code) throws IOException, InterruptedException {
        EventSource eventSource = EventSourceFactory.createEventSource(code, new StockDetailsHandler());
        eventSource.start();
        Thread.sleep(10000);
        eventSource.close();
        return "加载自选";
    }
}
