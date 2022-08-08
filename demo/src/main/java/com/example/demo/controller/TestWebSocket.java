package com.example.demo.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/1/11 14:26
 * @Version: 1.0.0
 * @Description: 描述
 */
@ServerEndpoint("/webSocket/{name}/{userName}")
@Component
public class TestWebSocket {

    private static int onlineCount = 0;

    private static Map<String,Map<String, TestWebSocket>> clients = new ConcurrentHashMap<String, Map<String, TestWebSocket>>();

    private Session session;

    private String name;

    @OnOpen
    public void onOpen(@PathParam("name")String name,@PathParam("userName")String userName, Session session){
                         this.session = session;
                         this.name = name;
                         if(clients.get(name) != null){
                             clients.get(name).put(userName,this);
                         }else{
                             Map<String,TestWebSocket> group = new ConcurrentHashMap<>();
                             group.put(userName,this);
                             clients.put(name,group);
                         }
                         addOnlineCount();
    }

    @OnMessage
    public void OnMessage(@PathParam("name")String name, Session session,String message){
               Map<String,String> map = JSONObject.parseObject(message, Map.class);
               Map<String, TestWebSocket> stringTestWebSocketMap = clients.get(name);
               stringTestWebSocketMap.keySet().stream().forEach( t ->{
                   sendMessage(name,t,map.get("userName"),map.get("message"));
               });

    }

    @OnError
    public void OnError(@PathParam("name")String name, Session session,Throwable e){
                 e.printStackTrace();
    }

    @OnClose
    public void OnClose(@PathParam("name")String name,Session session) throws IOException {
                        session.close();
                        clients.remove(name);
                        delOnlineCount();
    }

    public static void sendMessage(String name,String userName,String sendUserName,String text){
        Map<String, TestWebSocket> stringTestWebSocketMap = clients.get(name);
        if(stringTestWebSocketMap != null){
            TestWebSocket testWebSocket = stringTestWebSocketMap.get(userName);
            if(testWebSocket != null){
                Map<String,Object> map = new HashMap<>();
                map.put("sendUserName",sendUserName);
                map.put("message",text);
                testWebSocket.session.getAsyncRemote().sendText(JSONUtils.toJSONString(map));
            }
        }
    }


    public static void sendMessageAll(String name,String sendUserName,String text){
        Map<String, TestWebSocket> stringTestWebSocketMap = clients.get(name);
        stringTestWebSocketMap.keySet().stream().forEach( t ->{
            sendMessage(name,t,sendUserName,text);
        });
    }

    public static synchronized void addOnlineCount() {

        TestWebSocket.onlineCount++;

    }

    public static synchronized void delOnlineCount() {

        TestWebSocket.onlineCount--;

    }

}
