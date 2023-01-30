package com.woshen.stock.xxljob;

import com.launchdarkly.eventsource.EventSource;
import com.woshen.stock.core.EventSourceFactory;
import com.woshen.stock.handler.StockZixuanHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/1/30 0:26
*@version 1.0
*@description
*/
@Component
public class StockRedJob {


    @XxlJob("stockRedJob")
    public void exec(String... params) throws IOException, InterruptedException {
        Document document = Jsoup.connect("http://guba.eastmoney.com/remenba.aspx?type=1").get();
        Element hot = document.selectFirst(".hot");
        Element list = hot.selectFirst(".list");
        Elements aList = list.select("a");
        StringBuilder codes = new StringBuilder();
        for (Element a:aList
             ) {
            String text = a.text();
            String[] split = text.split("\\)");
            String code = split[0].replace("(", "");
            //创业板股票收录
            if(code.startsWith("3")){
                continue;
            }
            code = code.startsWith("6")?("1."+code):("0."+code);
            //String stockName = split[1].replace("吧", "");
            codes.append(code);
            codes.append(",");
        }
        String secids = "";
        if(codes.length() >0){
           secids = codes.substring(0,codes.length()-1);
        }

        EventSource eventSource = EventSourceFactory.createEventSource(secids, new StockZixuanHandler());
        eventSource.start();
        Thread.sleep(10000);
        eventSource.close();
    }
}
