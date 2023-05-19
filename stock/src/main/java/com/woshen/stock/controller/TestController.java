package com.woshen.stock.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.launchdarkly.eventsource.EventSource;
import com.woshen.stock.constant.PriceChangeType;
import com.woshen.stock.core.EventSourceFactory;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.handler.StockDetailsHandler;
import com.woshen.stock.handler.StockZixuanHandler;
import com.woshen.stock.server.IStockDayInformationService;
import com.woshen.stock.xxljob.StockDayImformationJob;
import com.woshen.stock.xxljob.StockRedJob;
import com.woshen.stock.xxljob.StockTrendsJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    private StockTrendsJob stockTrendsJob;

    @Autowired
    private StockDayImformationJob stockDayImformationJob;

    @Autowired
    private StockRedJob stockRedJob;

    @Autowired
    private IStockDayInformationService stockDayInformationServiceImpl;



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

    @RequestMapping("loadZD")
    @ResponseBody
    public String loadZD(){
        int pageIndex = 1;
        while (true){
            StockDayInformation stockDayInformation = new StockDayInformation();
            stockDayInformation.setOrderBy("id.asc");
            stockDayInformation.getPageInfo().setPageIndex(pageIndex);
            stockDayInformation.getPageInfo().setPageSize(200);
            Page<StockDayInformation> stockDayInformationPage = stockDayInformationServiceImpl.selectPage(stockDayInformation);
            if(!CollectionUtils.isEmpty(stockDayInformationPage.getRecords())){
                stockDayInformationPage.getRecords().stream().forEach(t->{
                    if(t.getPriceChange() != null && t.getTodayClose() != null){
                        double priceChange = t.getPriceChange().doubleValue();
                        double todayClose = t.getTodayClose().doubleValue();
                        if(priceChange == 0){
                            t.setPriceChangeType(PriceChangeType.FLAT);
                        }else if(priceChange > 0 && t.getClose() != null){
                            BigDecimal zt = t.getClose().multiply(BigDecimal.valueOf(1.1)).setScale(2, RoundingMode.HALF_UP);
                            if(todayClose >= zt.doubleValue()){
                                t.setPriceChangeType(PriceChangeType.LIMIT_UP);
                            }else{
                                t.setPriceChangeType(PriceChangeType.RISE);
                            }
                        }else if(priceChange < 0 && t.getClose() != null){
                            BigDecimal dt = t.getClose().multiply(BigDecimal.valueOf(0.9)).setScale(2, RoundingMode.HALF_UP);
                            if(todayClose <= dt.doubleValue()){
                                t.setPriceChangeType(PriceChangeType.LIMIT_DOWN);
                            }else{
                                t.setPriceChangeType(PriceChangeType.FALL);
                            }
                        }
                    }
                    if(t.getAverage() == null){
                        BigDecimal turnover = t.getTurnover();
                        Integer volume = t.getVolume();
                        if(turnover != null && volume != null && volume > 0){
                            BigDecimal divide = turnover.divide(BigDecimal.valueOf(100 * volume), 2, RoundingMode.HALF_UP);
                            t.setAverage(divide);
                        }
                    }
                    StockDayInformation stockDayInformation1 = new StockDayInformation();
                    stockDayInformation1.setId(t.getId());
                    stockDayInformation1.setAverage(t.getAverage());
                    stockDayInformation1.setPriceChangeType(t.getPriceChangeType());
                    stockDayInformationServiceImpl.updateById(stockDayInformation1);
                });

            }
            pageIndex ++;
            if(!stockDayInformationPage.hasNext()){
                break;
            }
        }

        return "加载自选";
    }
}
