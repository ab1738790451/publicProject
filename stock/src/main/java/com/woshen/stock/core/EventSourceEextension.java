package com.woshen.stock.core;

import com.launchdarkly.eventsource.MessageEvent;
import okhttp3.HttpUrl;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/1/18 15:48
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface EventSourceEextension {
    /**
     *连接成功处理方法
     * @throws Exception
     */
    void onOpen() throws Exception;

    /**
     * 连接关闭处理方法
     * @throws Exception
     */
    void onClosed() throws Exception;

    /**
     * 接受消息处理
     * @param s
     * @param messageEvent
     * @throws Exception
     */
    void onMessage(String s, MessageEvent messageEvent) throws Exception;

    /**
     *Comment
     * @param s
     * @throws Exception
     */
    void onComment(String s) throws Exception;

    /**
     * 失败处理
     * @param throwable
     */
    void onError(Throwable throwable);

    /**
     * 连接的url
     * @return
     */
    HttpUrl getUrl(String code);
}
