package com.example.demo.push.model;

import com.example.demo.push.constants.PushClickType;
import com.example.demo.push.constants.PushType;
import lombok.Data;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/8/2 15:25
 * @Version: 1.0.0
 * @Description: 推送的安卓配置入参
 */
@Data
public class AndroidConfigModel {
    /**
     * 通知标题
     */
    private String title;
    /**
     * 通知内容
     */
    private String content;
    /**
     * 通知后打开的应用内页面
     */
    private String intent;
    /**
     * 通知后打开的链接
     */
    private String url;
    /**
     * 通知的后续动作
     */
    private PushClickType ClickType;
    /**
     * 推送类型
     */
    private PushType pushType;
    /**
     * 小米私有消息设置申请的channelId
     */
    private String xmChannelId;
    /**
     * oppo私有消息设置申请的channelId
     */
    private String oppoChannelId;
}
