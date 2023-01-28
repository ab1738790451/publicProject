package com.woshen.stock.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.launchdarkly.eventsource.MessageEvent;
import com.woshen.common.config.SpringUtils;
import com.woshen.stock.core.EventSourceEextension;
import com.woshen.stock.core.DfcfStockModel;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.entity.StockTimeSharing;
import com.woshen.stock.server.impl.StockDayInformationServiceImpl;
import com.woshen.stock.server.impl.StockTimeSharingServiceImpl;
import com.woshen.stock.utils.DongFangCaiFuUtils;
import okhttp3.HttpUrl;

import java.math.BigDecimal;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/1/18 16:42
 * @Version: 1.0.0
 * @Description: 描述
 */
public class StockDetailsHandler implements EventSourceEextension {

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
        JSONObject message = JSONObject.parseObject(messageEvent.getData());
        JSONObject data = (JSONObject) message.get("data");
        DfcfStockModel dfcfStockModel = data.toJavaObject(DfcfStockModel.class);
        if(index > 0){
            return;
        }
        index ++;
        StockDayInformationServiceImpl bean = SpringUtils.getBean(StockDayInformationServiceImpl.class);
        QueryWrapper<StockTimeSharing> sharingQueryWrapper  = new QueryWrapper<>();
        sharingQueryWrapper.eq("code",dfcfStockModel.getF57());
        sharingQueryWrapper.orderByDesc("date");
        sharingQueryWrapper.last("limit 1");
        StockTimeSharingServiceImpl stockTimeSharingService = SpringUtils.getBean(StockTimeSharingServiceImpl.class);
        StockTimeSharing timeSharing = stockTimeSharingService.getOne(sharingQueryWrapper);
        if(timeSharing == null){
            return;
        }
        QueryWrapper<StockDayInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",dfcfStockModel.getF57());
        queryWrapper.eq("transaction_date",timeSharing.getDate());
        StockDayInformation dayInformation = bean.getOne(queryWrapper);
        if(dayInformation != null){
            return;
        }
        StockDayInformation stockDayInformation = new StockDayInformation();
          stockDayInformation.setTransactionDate(timeSharing.getDate());
          stockDayInformation.setCode(dfcfStockModel.getF57());
          stockDayInformation.setClose(new BigDecimal(dfcfStockModel.getF60()));
          stockDayInformation.setOpen(new BigDecimal(dfcfStockModel.getF46()));
          stockDayInformation.setTodayClose(new BigDecimal(dfcfStockModel.getF43()));
          stockDayInformation.setHigh(new BigDecimal(dfcfStockModel.getF44()));
          stockDayInformation.setLow(new BigDecimal(dfcfStockModel.getF45()));
          stockDayInformation.setVolume(dfcfStockModel.getF47());
          stockDayInformation.setTurnover(new BigDecimal(dfcfStockModel.getF48()));
          stockDayInformation.setTurnoverRate(new BigDecimal(dfcfStockModel.getF168()));
          stockDayInformation.setTotalWorth(new BigDecimal(dfcfStockModel.getF116()));
          stockDayInformation.setCirculationWorth(new BigDecimal(dfcfStockModel.getF117()));
          stockDayInformation.setPb(new BigDecimal(dfcfStockModel.getF167()));
          stockDayInformation.setSpe(new BigDecimal(dfcfStockModel.getF163()));
          stockDayInformation.setPe(new BigDecimal(dfcfStockModel.getF162()));
          stockDayInformation.setPriceChange(new BigDecimal(dfcfStockModel.getF170()));
          stockDayInformation.setSuperInflow(dfcfStockModel.getF138().longValue());
          stockDayInformation.setSuperOutflow(dfcfStockModel.getF139().longValue());
          stockDayInformation.setMaxInflow(dfcfStockModel.getF141().longValue());
          stockDayInformation.setMaxOutflow(dfcfStockModel.getF142().longValue());
          stockDayInformation.setMiddleInflow(dfcfStockModel.getF144().longValue());
          stockDayInformation.setMiddleOutflow(dfcfStockModel.getF145().longValue());
          stockDayInformation.setMinInflow(dfcfStockModel.getF147().longValue());
          stockDayInformation.setMinOutflow(dfcfStockModel.getF148().longValue());
          stockDayInformation.setTotalCapitalStock(dfcfStockModel.getF277().longValue());
          stockDayInformation.setTotalRevenue(new BigDecimal(dfcfStockModel.getF183()));
          stockDayInformation.setCirculationCapitalStock(dfcfStockModel.getF85().longValue());
          stockDayInformation.setInterestRate(new BigDecimal(dfcfStockModel.getF186()));
          stockDayInformation.setNetInterestRate(new BigDecimal(dfcfStockModel.getF187()));
          stockDayInformation.setDebtRatio(new BigDecimal(dfcfStockModel.getF188()));
          stockDayInformation.setBonus(new BigDecimal(dfcfStockModel.getF190()));
          bean.save(stockDayInformation);
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
        return DongFangCaiFuUtils.getSocketDetailHttpUrl(code);
    }

}
