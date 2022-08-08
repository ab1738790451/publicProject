package com.example.demo.push.model;

import com.example.demo.push.constants.PushClickType;
import lombok.Data;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/7/29 13:43
 * @Version: 1.0.0
 * @Description: 描述
 */
@Data
public class PushForm {
    /**
     * 当前应用在推送服务的应用id（必填）
     */
    private Integer appId;

    /**
     * 推送使用的配置id（必填）
     */
    private Integer configId;

    /**
     * 密钥（必填）
     */
    private String appSecret;

    /**
     * 推送的客户cid（当单用户推送时必填）
     */
    private String cid;

    /**
     * 群推的客户cid集合（当多用户推送时必填）
     */
    private List<String> cids;

    /**
     * 推送的组名 （多个消息任务可以用同一个任务组名，后续可根据任务组名查询推送情况（长度限制100字符，且不能含有特殊符号）只允许填写数字、字母、横杠、下划线）
     */
    private String groupName;
    /**
     * 推送的标题（必填）
     */
    private String title;

    /**
     * 推送的内容（必填）
     */
    private String content;

    /**
     * 只推个推不开启离线厂商通道
     */
    //private Bool onlyGetui;


    /**
     * 推送的内容的后续动作
     */
    private PushClickType clickType;

    /**
     * 当clickType 为 intent 和 url 时必填（安卓）
     */
    private String clickContent;

    /**
     * 当clickType 为 intent 和 url 时必填（ios）
     */
    private String iosClickContent;
}
