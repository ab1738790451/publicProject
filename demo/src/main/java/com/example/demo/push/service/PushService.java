package com.example.demo.push.service;

import com.example.demo.common.TaskUtil;
import com.example.demo.push.constants.PushClickType;
import com.example.demo.push.constants.PushType;
import com.example.demo.push.factory.GetuiPushApiFactory;
import com.example.demo.push.factory.GetuiPushApiInstance;
import com.example.demo.push.model.AndroidConfigModel;
import com.example.demo.push.model.PushForm;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.AudienceDTO;
import com.getui.push.v2.sdk.dto.req.Settings;
import com.getui.push.v2.sdk.dto.req.Strategy;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
import com.getui.push.v2.sdk.dto.req.message.android.GTNotification;
import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
import com.getui.push.v2.sdk.dto.req.message.android.Ups;
import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
import com.getui.push.v2.sdk.dto.res.TaskIdDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/7/29 18:13
 * @Version: 1.0.0
 * @Description: 描述
 */
@Service
public class PushService {


    /**
     * 每次推送都要求一个不大于32位的请求标识
     * @return
     */
    private String getRequestId(){
        return UUID.randomUUID().toString().substring(0,29)+ LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    private void notNull(Object obj,String errMsg,String message){
         if(obj == null){
             throw new RuntimeException(errMsg+","+message);
         }
    }

    private void notEmpty(String str,String errMsg,String message){
        if(StringUtils.isBlank(str)){
            throw new RuntimeException(errMsg+","+message);
        }
    }

    private void notEmpty(Collection collection, String errMsg, String message){
        if(CollectionUtils.isEmpty(collection)){
            throw new RuntimeException(errMsg+","+message);
        }
    }

    private void isFalse(boolean bool,String errMsg,String message){
        if(bool){
            throw new RuntimeException(errMsg+","+message);
        }
    }
    /**
     * 参数检验
     * @param logUrl
     * @param form
     */
    private void checkParams(String logUrl, PushForm form, PushType pushType,Boolean isAsync){
        notNull(form,"PARAM_ERROR","参数不能为空");
        notNull(form.getAppId(), "PARAM_ERROR", "授权参数错误");
        notEmpty(form.getAppSecret(), "PARAM_ERROR", "授权参数错误");
        if(pushType.equals(PushType.MESSAGE) || pushType.equals(PushType.LIST)){
            notEmpty(form.getCids(),"PARAM_ERROR","推送的群体客户cids不能为空");
            if(isAsync != null && !isAsync) {
                isFalse(form.getCids().size() > 1000,"PARAM_ERROR","单次推送客户不能大于1000");
            }
        }else if(pushType.equals(PushType.SINGLE)){
            notEmpty(form.getCid(),"PARAM_ERROR","推送的单个客户cid不能为空");
        }
        notEmpty(form.getTitle(),"PARAM_ERROR","推送标题不能为空");
        notEmpty(form.getContent(),"PARAM_ERROR","推送内容不能为空");

        isFalse(form.getTitle().length() > 20,"PARAM_ERROR","标题长度不能大于20");
        isFalse(form.getContent().length() > 50,"PARAM_ERROR","消息内容长度不能大于50");
        if(form.getClickType() != null && (form.getClickType().equals(PushClickType.intent) || form.getClickType().equals(PushClickType.url)) ){
            notEmpty(form.getClickContent(),"PARAM_ERROR","android链接地址长度不能为空");
            isFalse(form.getClickContent().length() > 1024,"PARAM_ERROR","android链接地址长度不能大于1024");
            notEmpty(form.getIosClickContent(),"PARAM_ERROR","IOS链接地址长度不能为空");
        }
        if(form.getClickType() != null && (form.getClickType().equals(PushClickType.payload) || form.getClickType().equals(PushClickType.payload_custom))){
            notEmpty(form.getPayload(),"PARAM_ERROR","payload不能为空");
            isFalse(form.getPayload().length() > 3072,"PARAM_ERROR","payload的长度不能大于3072");
        }
        if(StringUtils.isNotBlank(logUrl)){
            isFalse(logUrl.length() > 256,"PARAM_ERROR","通知栏图标的地址长度不能大于256");
        }
        String groupName = form.getGroupName();
        if(StringUtils.isNotBlank(groupName) ){
            isFalse(groupName.length() > 100,"PARAM_ERROR","任务组名长度不能大于100");
            int length = groupName.replaceAll("[0-9a-zA-Z\\-_]", "").length();
            if(length >0 ){
                isFalse(groupName.length() > 0,"PARAM_ERROR","任务组名只能为数字、字母、下划线、横线");
            }
        }
    }

    /**
     * 单个用户推送
     * @param form
     * @return
     */
    public ApiResult pushSingle(PushForm form){
        GetuiPushApiInstance getuiPushApiInstance = GetuiPushApiFactory.getPushApiInstance();
        notNull(getuiPushApiInstance,"INSTANCE_ERROR","PushApiInstance初始化失败");
        PushApi pushApi = getuiPushApiInstance.getApi(PushApi.class);
        notNull(pushApi,"INSTANCE_ERROR","pushApi初始化失败");
        String logUrl = getuiPushApiInstance.getLogUrl();
        String xmChannelId = getuiPushApiInstance.getXmChannelId();
        String oppoChannelId = getuiPushApiInstance.getOppoChannelId();
        checkParams(logUrl,form,PushType.SINGLE,null);
        PushDTO<Audience> pushDTO = getPushDTO(form,logUrl,PushType.SINGLE,xmChannelId,oppoChannelId);
        //设置推送用户
        Audience audience = new Audience();
        audience.addCid(form.getCid());
        pushDTO.setAudience(audience);

        ApiResult<Map<String, Map<String, String>>> mapApiResult = pushApi.pushToSingleByCid(pushDTO);
        return mapApiResult;
    }


    /**
     * 创建多推消息体
     * @param pushApi
     * @param form
     * @param logUrl
     * @param xmChannelId
     * @param oppoChannelId
     * @return
     */
    public Map<String,String> createMessage(PushApi pushApi, PushForm form, String logUrl, String xmChannelId, String oppoChannelId){
        PushDTO pushDTO = getPushDTO(form, logUrl,PushType.MESSAGE,xmChannelId,oppoChannelId);
        Map<String,String>  result = new HashMap<>();
        ApiResult<TaskIdDTO> msg = pushApi.createMsg(pushDTO);

        if(msg.getCode() == 0){
            result.put("taskId",msg.getData().getTaskId());
        }else{
            result.put("errorMsg",msg.getMsg());

        }
        return result;
    }

    /**
     * 群推（限制最大推送用户数为1000，超出请使用异步推送）
     * @param form
     * @return
     */
    public ApiResult pushList(PushForm form){
        GetuiPushApiInstance getuiPushApiInstance = GetuiPushApiFactory.getPushApiInstance();
        notNull(getuiPushApiInstance,"INSTANCE_ERROR","PushApiInstance初始化失败");
        PushApi pushApi = getuiPushApiInstance.getApi(PushApi.class);
        notNull(pushApi,"INSTANCE_ERROR","pushApi初始化失败");
        String logUrl = getuiPushApiInstance.getLogUrl();
        String xmChannelId = getuiPushApiInstance.getXmChannelId();
        String oppoChannelId = getuiPushApiInstance.getOppoChannelId();
        checkParams(logUrl,form,PushType.LIST,false);
        Map<String,String> msgMap = createMessage(pushApi, form, logUrl,xmChannelId,oppoChannelId);
        String taskId = msgMap.get("taskId");
        if(StringUtils.isBlank(taskId)){
            String errorMsg = msgMap.get("errorMsg");
            throw new RuntimeException(errorMsg);
        }
        AudienceDTO audienceDTO = new AudienceDTO();
        audienceDTO.setAsync(false);
        Audience audience = new Audience();
        audience.setCid(form.getCids());
        audienceDTO.setAudience(audience);
        audienceDTO.setTaskid(taskId);
        //添加调用记录
        ApiResult<Map<String, Map<String, String>>> mapApiResult = pushApi.pushListByCid(audienceDTO);
        return mapApiResult;
    }

    /**
     * 多用户异步推送（推荐单次推送客户大于1000时使用）
     * @param form
     * @return
     */
    public void asyncPushList(PushForm form){
        GetuiPushApiInstance getuiPushApiInstance = GetuiPushApiFactory.getPushApiInstance();
        notNull(getuiPushApiInstance,"INSTANCE_ERROR","PushApiInstance初始化失败");
        PushApi pushApi = getuiPushApiInstance.getApi(PushApi.class);
        notNull(pushApi,"INSTANCE_ERROR","pushApi初始化失败");
        String logUrl = getuiPushApiInstance.getLogUrl();
        String xmChannelId = getuiPushApiInstance.getXmChannelId();
        String oppoChannelId = getuiPushApiInstance.getOppoChannelId();
        checkParams(logUrl,form,PushType.LIST,true);
        Map<String,String> msgMap = createMessage(pushApi, form, logUrl,xmChannelId,oppoChannelId);
        String taskId = msgMap.get("taskId");
        if(StringUtils.isBlank(taskId)){
            String errorMsg = msgMap.get("errorMsg");
            throw new RuntimeException(errorMsg);
        }
        int i = form.getCids().size() / 1000;
        List<String> cids = form.getCids();
        for (int index = 0;index <= i;index ++){
            int startIndex = index * 1000;
            int endIndex = form.getCids().size() > 1000? (index +1) * 1000:form.getCids().size();
            List<String> list = cids.subList(startIndex, endIndex);
            TaskUtil.exec(() ->{
                AudienceDTO audienceDTO = new AudienceDTO();
                audienceDTO.setAsync(false);
                Audience audience = new Audience();
                audience.setCid(list);
                audienceDTO.setAudience(audience);
                audienceDTO.setTaskid(taskId);
                form.setCids(list);
                ApiResult<Map<String, Map<String, String>>> mapApiResult = pushApi.pushListByCid(audienceDTO);
            });
        }
    }


    public ApiResult pushAll(PushForm form){
        GetuiPushApiInstance getuiPushApiInstance = GetuiPushApiFactory.getPushApiInstance();
        notNull(getuiPushApiInstance,"INSTANCE_ERROR","PushApiInstance初始化失败");
        PushApi pushApi = getuiPushApiInstance.getApi(PushApi.class);
        notNull(pushApi,"INSTANCE_ERROR","pushApi初始化失败");
        String logUrl = getuiPushApiInstance.getLogUrl();
        String xmChannelId = getuiPushApiInstance.getXmChannelId();
        String oppoChannelId = getuiPushApiInstance.getOppoChannelId();
        checkParams(logUrl,form,PushType.ALL,null);
        PushDTO pushDTO = getPushDTO(form, logUrl, PushType.ALL, xmChannelId, oppoChannelId);
        pushDTO.setAudience("All");

        ApiResult apiResult = pushApi.pushAll(pushDTO);

        return apiResult;
    }

    /**
     * 调用参数设置
     * @param form
     * @param logUrl
     * @return
     */
    private PushDTO getPushDTO(PushForm form, String logUrl, PushType pushType, String xmChannelId, String oppoChannelId){

        PushDTO pushDTO = new PushDTO<>();

        //设置消息最大离线时间 最多只能3天
        Settings settings = new Settings();
        settings.setTtl(3*24*3600);
        //ios只从厂商通道下发
        Strategy strategy = new Strategy();
        strategy.setIos(2);
        settings.setStrategy(strategy);
        pushDTO.setSettings(settings);

        //设置唯一请求标识
        pushDTO.setRequestId(getRequestId());

        //推送任务组名
        String groupName = form.getGroupName();
        if(StringUtils.isNotBlank(groupName)){
            pushDTO.setGroupName(groupName);
        }

        String url = null;
        String intent = null;
        String payload = null;
        //个推通道设置
        PushMessage pushMessage = new PushMessage();
        GTNotification gtNotification = new GTNotification();
        gtNotification.setTitle(form.getTitle());
        gtNotification.setBody(form.getContent());
        //设置通知栏图标
        if(StringUtils.isNotBlank(logUrl)){
            gtNotification.setLogoUrl(logUrl);
        }
        //根据通知类型设置后续动作
        PushClickType clickType = form.getClickType();
        if(clickType == null || clickType.equals(PushClickType.none)){
            gtNotification.setClickType(PushClickType.none.name());
        }else{
            gtNotification.setClickType(clickType.name());
            if(clickType.equals(PushClickType.intent)){
                gtNotification.setIntent(form.getClickContent());
                intent = form.getClickContent();
            }else if(clickType.equals(PushClickType.url)){
                gtNotification.setUrl(form.getClickContent());
                url = form.getClickContent();
            }
            payload = form.getIosClickContent();
        }
        //角标设置
        //gtNotification.setBadgeAddNum("1");
        pushMessage.setNotification(gtNotification);
        pushDTO.setPushMessage(pushMessage);

        //厂商通道设置
        PushChannel pushChannel = new PushChannel();
        //ios设置
        pushChannel.setIos(iodConfig(form.getTitle(),form.getContent(),payload));

        //安卓设置
        AndroidConfigModel androidConfigModel = new AndroidConfigModel();
        androidConfigModel.setTitle(form.getTitle());
        androidConfigModel.setContent(form.getContent());
        androidConfigModel.setUrl(url);
        androidConfigModel.setIntent(intent);
        androidConfigModel.setClickType(clickType);
        androidConfigModel.setPushType(pushType);
        androidConfigModel.setXmChannelId(xmChannelId);
        androidConfigModel.setOppoChannelId(oppoChannelId);
        pushChannel.setAndroid(androidConfig(androidConfigModel));
        pushDTO.setPushChannel(pushChannel);
        return pushDTO;
    }


    /**
     * 安卓设置
     * @param androidConfig
     * @return
     */
    public AndroidDTO androidConfig(AndroidConfigModel androidConfig){
        String title = androidConfig.getTitle();
        String content = androidConfig.getContent();
        PushClickType clickType = androidConfig.getClickType();
        String url = androidConfig.getUrl();
        String intent = androidConfig.getIntent();
        PushType pushType = androidConfig.getPushType();
        String xmChannelId = androidConfig.getXmChannelId();
        String oppoChannelId = androidConfig.getOppoChannelId();
        AndroidDTO androidDTO = new AndroidDTO();
         Ups ups = new Ups();
         ThirdNotification thirdNotification = new ThirdNotification();
         thirdNotification.setTitle(title);
         thirdNotification.setBody(content);
         if(PushClickType.intent.equals(clickType) || PushClickType.url.equals(clickType)){
             thirdNotification.setClickType(clickType.name());
         }else{
             thirdNotification.setClickType(PushClickType.startapp.name());
         }
         if(StringUtils.isNotBlank(url)){
             thirdNotification.setUrl(url);
         }
         if(StringUtils.isNotBlank(intent)){
             thirdNotification.setIntent(intent);
         }
         ups.setNotification(thirdNotification);

         //厂商options设置
          Map<String, Map<String, Object>> options = new HashMap<>();

          //华为角标的设置
         Map<String,Object> hw = new HashMap<>();
         hw.put("/message/android/notification/badge/class","io.dcloud.PandoraEntry"); //应用入口Activity路径名称
         hw.put("/message/android/notification/badge/add_num",1);//应用角标累加数字，并非应用角标实际显示数字必须是大于0小于100的整数
         if(PushType.SINGLE.equals(pushType)){
            hw.put("/message/android/notification/importance","NORMAL");//设置消息类型为服务与通讯
         }
         options.put("HW",hw);
        //荣耀角标的设置
        Map<String,Object> ho = new HashMap<>();
        ho.put("/android/notification/badge/badgeClass","io.dcloud.PandoraEntry"); //应用入口Activity路径名称
        ho.put("/android/notification/badge/addNum",1);//应用角标累加数字，并非应用角标实际显示数字必须是大于0小于100的整数
        if(PushType.SINGLE.equals(pushType)){
            ho.put("/android/notification/importance","NORMAL");//设置消息类型为服务与通讯
        }
        options.put("HO",ho);
         //当消息推送为单人时设置私有消息，减少公有消息的次数消耗
         if(PushType.SINGLE.equals(pushType)){
             //小米重要消息设置
             if(StringUtils.isNotBlank(xmChannelId)){
                 Map<String,Object> xm = new HashMap<>();
                 xm.put("/extra.channel_id",xmChannelId);
                 options.put("XM",xm);
             }

             //oppo重要消息设置
             if(StringUtils.isNotBlank(oppoChannelId)){
                 Map<String,Object> oppo = new HashMap<>();
                 oppo.put("/channel_id",oppoChannelId);
                 options.put("OP",oppo);
             }

             //vivo 重要消息设置(单人)
             Map<String,Object> vivo = new HashMap<>();
             vivo.put("/classification",1);
             options.put("VV",vivo);
         }
         ups.setOptions(options);


         androidDTO.setUps(ups);
         return androidDTO;
    }

    /**
     * ios设置
     * @param title
     * @param content
     * @param payload 点击后续动作
     * @return
     */
    public IosDTO iodConfig(String title, String content, String payload){
        IosDTO iosDTO = new IosDTO();
        //设置角标
        iosDTO.setAutoBadge("+1");
        Aps aps = new Aps();
        Alert alert = new Alert();
        alert.setTitle(title);
        alert.setBody(content);
        aps.setAlert(alert);
        iosDTO.setPayload(payload);
        return iosDTO;
    }

}
