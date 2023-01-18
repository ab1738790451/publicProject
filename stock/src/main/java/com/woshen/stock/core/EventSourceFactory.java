package com.woshen.stock.core;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import okhttp3.HttpUrl;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/1/18 16:08
 * @Version: 1.0.0
 * @Description: 描述
 */
public class EventSourceFactory {

    private EventSourceFactory(){}

    public static EventSource createEventSource(EventSourceEextension eventSourceEextension){
        EventHandler eventHandler =   new EventHandler() {
            @Override
            public void onOpen() throws Exception {
                eventSourceEextension.onOpen();
            }

            @Override
            public void onClosed() throws Exception {
                eventSourceEextension.onClosed();
            }

            @Override
            public void onMessage(String s, MessageEvent messageEvent) throws Exception {
                eventSourceEextension.onMessage(s,messageEvent);
            }

            @Override
            public void onComment(String s) throws Exception {
                eventSourceEextension.onComment(s);
            }

            @Override
            public void onError(Throwable throwable) {
                eventSourceEextension.onError(throwable);
            }
        };
        return new EventSource.Builder(eventHandler, eventSourceEextension.getUrl()).build();
    }
}
