package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.LocalCahceUtil;
import com.example.demo.responseResult.ResponseResult;
import com.example.demo.utils.ByteUtil;
import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import com.getui.push.v2.sdk.api.AuthApi;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.AudienceDTO;
import com.getui.push.v2.sdk.dto.req.AuthDTO;
import com.getui.push.v2.sdk.dto.req.Settings;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
import com.getui.push.v2.sdk.dto.req.message.android.GTNotification;
import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
import com.getui.push.v2.sdk.dto.req.message.android.Ups;
import com.getui.push.v2.sdk.dto.res.TaskIdDTO;
import com.getui.push.v2.sdk.dto.res.TokenDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/7/22 16:13
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("getui")
@Controller
public class GetuiController {

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "方法一",tags = "方法一",httpMethod = "GET")
    @RequestMapping(value = "/one",method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response){
        return "getui";
    }



    @RequestMapping("testYunCloud")
    @ResponseBody
    public ResponseResult testYunCloud(){

        String appkey = "01YPlpMP1vA6ZLH5E2dBX8";
        long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String mastersecret = "SSvQwxgvMZ9SjTt9lkxz98";

       /* AuthApi authApi = getApi(AuthApi.class);
        String signStr = appkey + timestamp + mastersecret;
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        instance.update(signStr.getBytes("UTF-8"));
        String sign = ByteUtil.byteHex(instance.digest());

        AuthDTO authDTO = new AuthDTO();
        authDTO.setAppkey(appkey);
        authDTO.setSign(sign);
        authDTO.setTimestamp(timestamp);
        ApiResult<TokenDTO> auth = authApi.auth(authDTO);
        TokenDTO result = auth.getData();
        String token = result.getToken();
        System.err.println(token);
        Long expireTime = result.getExpireTime();*/
       /* Map<String,Object> params = new HashMap<>();
        params.put("sign",sign);
        params.put("timestamp",timestamp);
        params.put("appkey",appkey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> header = new HttpEntity<>(JSONObject.toJSONString(params),headers);
        String s = restTemplate.postForObject("https://restapi.getui.com/v2/8IywH0Z6Xm7UceGhm5LpM4/auth", header, String.class);
        Map map = JSONObject.parseObject(s, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        long expire_time = Long.valueOf((String)data.get("expire_time"));
        String token = (String) data.get("token");
        long l = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long l1 = (expireTime - l) / 60;*/

        Map<String,Object> params = new HashMap<>();
        params.put("appId","__UNI__EE500CC");
        String s = restTemplate.postForObject("https://tcb-tx949ubh006979-6dplm23e07d6a.service.tcloudbase.com/getPushManager", params, String.class);
        return new ResponseResult(200,"成功");
    }



    @RequestMapping("getToken")
    @ResponseBody
    public ResponseResult getToken() throws Exception {

        String appkey = "01YPlpMP1vA6ZLH5E2dBX8";
        long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String mastersecret = "SSvQwxgvMZ9SjTt9lkxz98";

        AuthApi authApi = getApi(AuthApi.class);
        String signStr = appkey + timestamp + mastersecret;
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        instance.update(signStr.getBytes("UTF-8"));
        String sign = ByteUtil.byteHex(instance.digest());

        AuthDTO authDTO = new AuthDTO();
        authDTO.setAppkey(appkey);
        authDTO.setSign(sign);
        authDTO.setTimestamp(timestamp);
        ApiResult<TokenDTO> auth = authApi.auth(authDTO);
        TokenDTO result = auth.getData();
        String token = result.getToken();
        System.err.println(token);
        Long expireTime = result.getExpireTime();
    /*    Map<String,Object> params = new HashMap<>();
        params.put("sign",sign);
        params.put("timestamp",timestamp);
        params.put("appkey",appkey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> header = new HttpEntity<>(JSONObject.toJSONString(params),headers);
        String s = restTemplate.postForObject("https://restapi.getui.com/v2/8IywH0Z6Xm7UceGhm5LpM4/auth", header, String.class);
        Map map = JSONObject.parseObject(s, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        long expire_time = Long.valueOf((String)data.get("expire_time"));
        String token = (String) data.get("token");*/
        long l = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long l1 = (expireTime - l) / 60;
        LocalCahceUtil.set("getui_token",token,(int) l1);
        return new ResponseResult(200,"成功");
    }

    @RequestMapping("delToken")
    @ResponseBody
    public ResponseResult delToken()  {
        String getui_token = getTokenStr();
        AuthApi api = getApi(AuthApi.class);
        ApiResult<Void> close = api.close(getui_token);
        int code = close.getCode();
        if(code == 0){
            return new ResponseResult(200,"成功");
        }else{
            return new ResponseResult(500,"失败");
        }
        /*ResponseEntity<String> exchange = restTemplate.exchange("https://restapi.getui.com/v2/8IywH0Z6Xm7UceGhm5LpM4/auth/" + getui_token, HttpMethod.DELETE, null, String.class);
        return new ResponseResult(200,"成功");*/
    }


    /**
     * 创建群推消息体
     * @param mes
     * @return
     */
    @RequestMapping("createMessage")
    @ResponseBody
    public ResponseResult createMessage(String mes){

        PushApi api = getApi(PushApi.class);
        PushDTO pushDTO = new PushDTO();
        pushDTO.setRequestId(UUID.randomUUID().toString());
        pushDTO.setGroupName("测试");
      /*  Audience audience = new Audience();
        audience.setAll("all");
        pushDTO.setAudience(audience);*/

        PushMessage pushMessage = new PushMessage();
        GTNotification gtNotification = new GTNotification();
        gtNotification.setTitle("群体通知");
        gtNotification.setBody(mes);
        gtNotification.setClickType("none");
        pushMessage.setNotification(gtNotification);
        pushDTO.setPushMessage(pushMessage);
        ApiResult<TaskIdDTO> msg = api.createMsg(pushDTO);
        int code = msg.getCode();
        /*Map<String,Object> params = new HashMap<>();
        params.put("request_id", UUID.randomUUID());

        params.put("group_name","测试");

        params.put("audience","all");

        JSONObject message = new JSONObject();
        JSONObject notification = new JSONObject();
        message.put("notification",notification);
        notification.put("title","通知");
        notification.put("body",mes);
        notification.put("click_type","none");
        params.put("push_message",message);

        //推送条件设置 {"ttl":30*30,"strategy":}
        //params.put("settings","");

        String url = "https://restapi.getui.com/v2/8IywH0Z6Xm7UceGhm5LpM4/push/list/message";
        HttpHeaders headers = new HttpHeaders();
        String token = getTokenStr();
        headers.add("token",token);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> header = new HttpEntity<>(JSONObject.toJSONString(params),headers);
        String s = restTemplate.postForObject(url, header, String.class);
        Map map = JSONObject.parseObject(s, Map.class);
        String code = (String)map.get("code");*/

        if(code == 0) {
            TaskIdDTO data = msg.getData();
            String taskId = data.getTaskId();
            System.err.println(taskId);
            LocalCahceUtil.set("taskid",taskId,60*60);
            return new ResponseResult(200,"成功");
        }else{
            return new ResponseResult(500,"失败");
        }

    }


    /**
     * 推全部
     * @param mes
     * @return
     */
    @RequestMapping("getuiAll")
    @ResponseBody
    public ResponseResult getuiAll(String mes){
/*        Map<String,Object> params = new HashMap<>();
        params.put("request_id", UUID.randomUUID());

        params.put("group_name","测试");

        params.put("audience","all");

        JSONObject message = new JSONObject();
        JSONObject notification = new JSONObject();
        //message.put("transmission",mes);
        message.put("notification",notification);
        notification.put("title","通知");
        notification.put("body",mes);
        notification.put("click_type","none");
        params.put("push_message",message);

        JSONObject settings = new JSONObject();
        settings.put("ttl",-1);
        params.put("settings",settings);

        String url = "https://restapi.getui.com/v2/8IywH0Z6Xm7UceGhm5LpM4/push/all";
        HttpHeaders headers = new HttpHeaders();
        String token = getTokenStr();
        headers.add("token",token);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> header = new HttpEntity<>(JSONObject.toJSONString(params),headers);
        String s = restTemplate.postForObject(url, header, String.class);*/

        PushApi api = getApi(PushApi.class);
        PushDTO<String> pushDTO = new PushDTO();
        pushDTO.setRequestId(UUID.randomUUID().toString());
        pushDTO.setGroupName("测试");
        pushDTO.setAudience("all");

        PushMessage pushMessage = new PushMessage();
        GTNotification gtNotification = new GTNotification();
        gtNotification.setTitle("群体通知");
        gtNotification.setBody(mes);
        gtNotification.setClickType("none");
        pushMessage.setNotification(gtNotification);
        pushDTO.setPushMessage(pushMessage);
      /*  Settings settings = new Settings();
        settings.setTtl(-1);
        pushDTO.setSettings(settings);*/
        ApiResult<TaskIdDTO> taskIdDTOApiResult = api.pushAll(pushDTO);
        TaskIdDTO data = taskIdDTOApiResult.getData();
        return new ResponseResult(200,"成功");
    }


    /**
     * 推指定用户
     * @param cids
     * @param mes
     * @return
     */
    @RequestMapping("getuiByCids")
    @ResponseBody
    public ResponseResult getuiByCids(String[] cids,String mes){


       /* Map<String,Object> params = new HashMap<>();
        params.put("request_id", UUID.randomUUID());

        //同步异步
        params.put("is_async",false);

        JSONObject audience = new JSONObject();
        audience.put("cid",cids);
        params.put("audience",audience);

        //消息id
        params.put("taskid",LocalCahceUtil.get("taskid"));

        String url = "https://restapi.getui.com/v2/8IywH0Z6Xm7UceGhm5LpM4/push/list/cid";
        HttpHeaders headers = new HttpHeaders();
        String token = getTokenStr();
        headers.add("token",token);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> header = new HttpEntity<>(JSONObject.toJSONString(params),headers);
        String s = restTemplate.postForObject(url, header, String.class);*/

        PushApi api = getApi(PushApi.class);
        AudienceDTO audienceDTO = new AudienceDTO();
        audienceDTO.setAsync(false);
        Audience audience = new Audience();
        audience.setCid(Arrays.asList(cids));
        audienceDTO.setAudience(audience);
        audienceDTO.setTaskid( getTaskId());
        ApiResult<Map<String, Map<String, String>>> mapApiResult = api.pushListByCid(audienceDTO);
        if(mapApiResult.getCode() == 0){
            return new ResponseResult(200,"成功");
        }
        return new ResponseResult(500,"失败");
    }


    @RequestMapping("getuiSingle")
    @ResponseBody
    public ResponseResult getuiSingle(String cid,String mes){

        PushApi pushApi = getApi(PushApi.class);
        PushDTO<Audience> pushDTO = new PushDTO<>();
        pushDTO.setGroupName("测试");
        Audience audience = new Audience();
        audience.addCid(cid);
        pushDTO.setAudience(audience);
        PushMessage pushMessage = new PushMessage();
        GTNotification gtNotification = new GTNotification();
        gtNotification.setTitle("通知");
        gtNotification.setBody(mes);
        gtNotification.setClickType("none");

        gtNotification.setClickType("url");
        gtNotification.setUrl("https://www.baidu.com");
        pushMessage.setNotification(gtNotification);
        pushDTO.setPushMessage(pushMessage);
        pushDTO.setRequestId(UUID.randomUUID().toString());
        PushChannel pushChannel = new PushChannel();
        AndroidDTO android = new AndroidDTO();
        Ups ups = new Ups();
        ThirdNotification thirdNotification = new ThirdNotification();
        thirdNotification.setTitle("厂商通知");
        thirdNotification.setBody(mes);
        thirdNotification.setClickType("none");
        ups.setNotification(thirdNotification);
        android.setUps(ups);
        pushChannel.setAndroid(android);
        pushDTO.setPushChannel(pushChannel);
        ApiResult<Map<String, Map<String, String>>> mapApiResult = pushApi.pushToSingleByCid(pushDTO);

/*
        Map<String,Object> params = new HashMap<>();
        params.put("request_id", UUID.randomUUID());

        params.put("group_name","测试");
        JSONObject audience = new JSONObject();
        audience.put("cid",new String[]{cid});
        params.put("audience",audience);

        JSONObject message = new JSONObject();
        JSONObject notification = new JSONObject();
        message.put("notification",notification);
        notification.put("title","通知");
        notification.put("body",mes);
        notification.put("click_type","none");
        params.put("push_message",message);

        String url = "https://restapi.getui.com/v2/8IywH0Z6Xm7UceGhm5LpM4/push/single/cid";
        HttpHeaders headers = new HttpHeaders();
        String token = getTokenStr();
        headers.add("token",token);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> header = new HttpEntity<>(JSONObject.toJSONString(params),headers);
        String s = restTemplate.postForObject(url, header, String.class);*/
        return new ResponseResult(200,"成功");
    }

    public <T> T getApi(Class<T> cls){
      /*  String appkey = "01YPlpMP1vA6ZLH5E2dBX8";
        String mastersecret = "SSvQwxgvMZ9SjTt9lkxz98";
        String appId  = "8IywH0Z6Xm7UceGhm5LpM4";*/

        String appkey = "vGI777IBelAQYG4YxLz4EA";
        String mastersecret = "XT7uRdTf558gYdy2PUqYJ5";
        String appId  = "LoLktEtsuA6ho9u7BJbko2";
        GtApiConfiguration apiConfiguration = new GtApiConfiguration();
        apiConfiguration.setAppId(appId);
        apiConfiguration.setAppKey(appkey);
        apiConfiguration.setMasterSecret(mastersecret);
        // 接口调用前缀，请查看文档: 接口调用规范 -> 接口前缀, 可不填写appId
        apiConfiguration.setDomain("https://restapi.getui.com/v2/");
        ApiHelper apiHelper = ApiHelper.build(apiConfiguration);
       return apiHelper.creatApi(cls);
    }

    public String getTokenStr(){
        return LocalCahceUtil.get("getui_token") == null?"53c0f6e8e525073daa581ace136490d9e898b0313a50e9d4729523d917866623":LocalCahceUtil.get("getui_token");
    }

    public String getTaskId(){
        return LocalCahceUtil.get("taskid") == null?"53c0f6e8e525073daa581ace136490d9e898b0313a50e9d4729523d917866623":LocalCahceUtil.get("taskid");
    }
}
