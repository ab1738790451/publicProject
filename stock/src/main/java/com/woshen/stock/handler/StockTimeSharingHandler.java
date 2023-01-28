package com.woshen.stock.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.launchdarkly.eventsource.MessageEvent;
import com.sun.javafx.collections.MappingChange;
import com.woshen.common.config.SpringUtils;
import com.woshen.stock.core.EventSourceEextension;
import com.woshen.stock.core.DfcfStockTimeSharding;
import com.woshen.stock.entity.StockTimeSharing;
import com.woshen.stock.server.impl.StockTimeSharingServiceImpl;
import com.woshen.stock.utils.DongFangCaiFuUtils;
import okhttp3.HttpUrl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/1/18 16:42
 * @Version: 1.0.0
 * @Description: 描述
 */
public class StockTimeSharingHandler implements EventSourceEextension {
    private int index = 0;

    @Override
    public void onOpen() throws Exception {
        System.err.println("连接成功");
    }

    @Override
    public void onClosed() throws Exception {
        System.err.println("连接关闭");
    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        System.err.println("message-s:"+s);
        System.err.println("data:"+messageEvent.getData());
        if(index >0){
            return;
        }
        index ++;
        JSONObject message = JSONObject.parseObject(messageEvent.getData());
        JSONObject data = (JSONObject) message.get("data");
        DfcfStockTimeSharding dfcfStockTimeSharding = data.toJavaObject(DfcfStockTimeSharding.class);
        List<Map> hisPrePrices = JSONObject.parseArray(dfcfStockTimeSharding.getHisPrePrices(), Map.class);
        Object date = hisPrePrices.get(0).get("date");
        StockTimeSharing stockTimeSharing = new StockTimeSharing();
        stockTimeSharing.setCode(dfcfStockTimeSharding.getCode());
        LocalDate localDate = LocalDate.parse(String.valueOf(date), DateTimeFormatter.ofPattern("yyyyMMdd"));
        stockTimeSharing.setDate(localDate);
        stockTimeSharing.setTrends(JSONObject.toJSONString(dfcfStockTimeSharding.getTrends()));
        stockTimeSharing.setTrendsTotal(dfcfStockTimeSharding.getTrendsTotal());
        StockTimeSharingServiceImpl stockTimeSharingService = SpringUtils.getBean(StockTimeSharingServiceImpl.class);
        QueryWrapper<StockTimeSharing> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("date",localDate);
        queryWrapper.eq("code",dfcfStockTimeSharding.getCode());
        StockTimeSharing timeSharing = stockTimeSharingService.getOne(queryWrapper);
        if(timeSharing != null){
            return;
        }
        stockTimeSharingService.save(stockTimeSharing);
    }

    @Override
    public void onComment(String s) throws Exception {
        System.err.println("comment:"+s);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("error:"+throwable.getMessage());
    }

    @Override
    public HttpUrl getUrl(String code) {
        return DongFangCaiFuUtils.getSocketTimeSharingHttpUrl(code);
    }
}
