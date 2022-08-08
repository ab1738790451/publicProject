package com.example.demo.push.factory;

import com.getui.push.v2.sdk.ApiHelper;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/7/29 11:19
 * @Version: 1.0.0
 * @Description: 描述
 */
public class GetuiPushApiInstance {
    /**
     * 个推sdk核心 apiHelper
     */
    private ApiHelper apiHelper;

    /**
     * 个推通知时的通知栏图标
     */
    private String logUrl;

    /**
     * 小米单推时的 设置的channelId
     */
    private String xmChannelId;

    /**
     * oppo单推时设置的 channelId
     */
    private String oppoChannelId;

    public GetuiPushApiInstance(ApiHelper apiHelper, String logUrl, String xmChannelId, String oppoChannelId){
        this.apiHelper = apiHelper;
        this.logUrl = logUrl;
        this.xmChannelId = xmChannelId;
        this.oppoChannelId = oppoChannelId;
    }

    public <T> T getApi(Class<T> cls){
        return apiHelper.creatApi(cls);
    }

    public String getLogUrl() {
        return logUrl;
    }


    public String getXmChannelId() {
        return xmChannelId;
    }

    public String getOppoChannelId() {
        return oppoChannelId;
    }
}
