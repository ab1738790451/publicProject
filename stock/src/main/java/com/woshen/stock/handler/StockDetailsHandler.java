package com.woshen.stock.handler;

import com.alibaba.fastjson.JSONObject;
import com.launchdarkly.eventsource.MessageEvent;
import com.woshen.stock.core.EventSourceEextension;
import com.woshen.stock.entity.DfcfStockModel;
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
        System.err.println("解析后的值：\n"+dfcfStockModel.toString());
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
    public HttpUrl getUrl() {
        return DongFangCaiFuUtils.getSocketDetailHttpUrl("000980");
    }
}
