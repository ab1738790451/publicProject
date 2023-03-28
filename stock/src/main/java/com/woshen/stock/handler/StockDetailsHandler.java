package com.woshen.stock.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.launchdarkly.eventsource.MessageEvent;
import com.woshen.common.webcommon.utils.SpringUtils;
import com.woshen.stock.core.EventSourceEextension;
import com.woshen.stock.core.DfcfStockModel;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.entity.StockTimeSharing;
import com.woshen.stock.server.impl.StockDayInformationServiceImpl;
import com.woshen.stock.server.impl.StockTimeSharingServiceImpl;
import com.woshen.stock.utils.DongFangCaiFuUtils;
import okhttp3.HttpUrl;

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
          stockDayInformation.setClose(dfcfStockModel.getF60());
          stockDayInformation.setOpen(dfcfStockModel.getF46());
          stockDayInformation.setTodayClose(dfcfStockModel.getF43());
          stockDayInformation.setHigh(dfcfStockModel.getF44());
          stockDayInformation.setLow(dfcfStockModel.getF45());
          stockDayInformation.setAverage(dfcfStockModel.getF71());
          stockDayInformation.setVolume(dfcfStockModel.getF47());
          stockDayInformation.setTurnover(dfcfStockModel.getF48());
          stockDayInformation.setTurnoverRate(dfcfStockModel.getF168());
          stockDayInformation.setTotalWorth(dfcfStockModel.getF116());
          stockDayInformation.setCirculationWorth(dfcfStockModel.getF117());
          stockDayInformation.setPb(dfcfStockModel.getF167());
          stockDayInformation.setSpe(dfcfStockModel.getF163());
          stockDayInformation.setPe(dfcfStockModel.getF162());
          stockDayInformation.setPriceChange(dfcfStockModel.getF170());
          stockDayInformation.setSuperInflow(dfcfStockModel.getF138().longValue());
          stockDayInformation.setSuperOutflow(dfcfStockModel.getF139().longValue());
          stockDayInformation.setMaxInflow(dfcfStockModel.getF141().longValue());
          stockDayInformation.setMaxOutflow(dfcfStockModel.getF142().longValue());
          stockDayInformation.setMiddleInflow(dfcfStockModel.getF144().longValue());
          stockDayInformation.setMiddleOutflow(dfcfStockModel.getF145().longValue());
          stockDayInformation.setMinInflow(dfcfStockModel.getF147().longValue());
          stockDayInformation.setMinOutflow(dfcfStockModel.getF148().longValue());
          stockDayInformation.setTotalCapitalStock(dfcfStockModel.getF277().longValue());
          stockDayInformation.setTotalRevenue(dfcfStockModel.getF183());
          stockDayInformation.setCirculationCapitalStock(dfcfStockModel.getF85().longValue());
          stockDayInformation.setInterestRate(dfcfStockModel.getF186());
          stockDayInformation.setNetInterestRate(dfcfStockModel.getF187());
          stockDayInformation.setDebtRatio(dfcfStockModel.getF188());
          stockDayInformation.setBonus(dfcfStockModel.getF190());
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
