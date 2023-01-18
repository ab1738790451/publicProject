package com.woshen.stock.controller;

import com.launchdarkly.eventsource.EventSource;
import com.woshen.stock.core.EventSourceFactory;
import com.woshen.stock.handler.StockDetailsHandler;
import com.woshen.stock.handler.StockTimeSharingHandler;
import com.woshen.stock.server.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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

    private static EventSource eventSource;

    @RequestMapping("loadMenus")
    @ResponseBody
    public Map<String,Object> loadMenus(){
        Map<String,Object> map = new HashMap<>();
        map.put("data",menuServiceImpl.selectAll());
        return map;
    }


    @RequestMapping("loadStock")
    @ResponseBody
    public String loadStock(){
        eventSource = EventSourceFactory.createEventSource(new StockTimeSharingHandler());
        eventSource.start();
        return "连接成功";
    }

    @RequestMapping("closeStock")
    @ResponseBody
    public String closeStock(){
        eventSource.close();
        return "断开成功";
    }
}
