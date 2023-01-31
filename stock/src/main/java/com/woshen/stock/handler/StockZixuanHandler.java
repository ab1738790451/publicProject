package com.woshen.stock.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.launchdarkly.eventsource.MessageEvent;
import com.woshen.common.config.SpringUtils;
import com.woshen.stock.core.DfcfZixuanModel;
import com.woshen.stock.core.EventSourceEextension;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.server.impl.StockServiceImpl;
import com.woshen.stock.utils.BaseConfigUtils;
import com.woshen.stock.utils.DongFangCaiFuUtils;
import okhttp3.HttpUrl;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/1/29 1:04
*@version 1.0
*@description
*/
public class StockZixuanHandler implements EventSourceEextension {

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
        Integer total = (Integer) data.get("total");
        JSONObject diff = (JSONObject)data.get("diff");
        StockServiceImpl bean = SpringUtils.getBean(StockServiceImpl.class);
        for (int i = 0; i <total ; i++) {
            JSONObject item = (JSONObject)diff.get(String.valueOf(i));
            DfcfZixuanModel dfcfZixuanModel = item.toJavaObject(DfcfZixuanModel.class);
            Double f2 = dfcfZixuanModel.getF2();
            if(f2 > Integer.valueOf(BaseConfigUtils.getProperty("stock.red.maxprice","5000"))){
                continue;
            }
            QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code",dfcfZixuanModel.getF12());
            Stock one = bean.getOne(queryWrapper);
            if(one != null){
                continue;
            }
            Stock stock = new Stock();
            stock.setCode(dfcfZixuanModel.getF12());
            stock.setName(dfcfZixuanModel.getF14());
            stock.setIndustry(dfcfZixuanModel.getF100());
            stock.setType("GP");
            bean.save(stock);
        }
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
    public HttpUrl getUrl(String secids) {
        return DongFangCaiFuUtils.getSocketZixuanHttpUrl(secids);
    }
}
