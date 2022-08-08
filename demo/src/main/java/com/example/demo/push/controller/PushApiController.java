package com.example.demo.push.controller;

import com.example.demo.push.model.PushForm;
import com.example.demo.push.service.PushService;
import com.getui.push.v2.sdk.common.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/7/29 10:18
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("/push")
@RestController
public class PushApiController {

    @Autowired
    private PushService pushService;

    /**
     * 指定单个用户推送
     * @param form
     * @return
     */
    @RequestMapping("single")
    public ApiResult single(@RequestBody PushForm form){
        return   pushService.pushSingle(form);
    }

    /**
     * 指定多个用户群推（单次推送客户数不超过1000时使用）
     * @param form
     * @return
     */
    @RequestMapping("list")
    public ApiResult list(@RequestBody PushForm form){
            return pushService.pushList(form);
    }


    /**
     * 指定多个用户群推（异步，推荐单次推送客户大于1000时使用）
     * @param form
     * @return
     */
    @RequestMapping("asyncList")
    public String asyncList(@RequestBody PushForm form){
        try{
             pushService.asyncPushList(form);
        }catch (Exception e){
            return e.getMessage();
        }
        return "成功";
    }

    /**
     * 推送全部用户
     * @param form
     * @return
     */
    @RequestMapping("pushAll")
    public ApiResult pushAll(@RequestBody PushForm form){
        return pushService.pushAll(form);
    }
}
