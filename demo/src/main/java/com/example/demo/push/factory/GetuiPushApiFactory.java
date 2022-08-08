package com.example.demo.push.factory;

import com.alibaba.fastjson.JSONObject;
import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;


/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/7/29 10:28
 * @Version: 1.0.0
 * @Description: 描述
 */
public class GetuiPushApiFactory{

   private static  String domain = "https://restapi.getui.com/v2/";

   private static String appId = "si710BOoHy9seGvoUk1vH4";

   private static String appkey = "VOoT4BbESa7oaei9776RJ8";

   private static String  mastersecret = "5scBw8EGhu7CS9WKrOnAE2";

   private static  String logUrl = "http://static.csai.cn/csaicms/h5/daohang/dk/images/loan_log.png";

   private static String  xmChannelId = "";

   private static String  oppoChannelId = "";

    public static GetuiPushApiInstance  getPushApiInstance(){
        if(StringUtils.isBlank(domain) || StringUtils.isBlank(appId) || StringUtils.isBlank(appkey) || StringUtils.isBlank(mastersecret)){
            return null;
        }
        GtApiConfiguration apiConfiguration = new GtApiConfiguration();
        apiConfiguration.setDomain(domain);
        apiConfiguration.setAppId(appId);
        apiConfiguration.setAppKey(appkey);
        apiConfiguration.setMasterSecret(mastersecret);
        ApiHelper apiHelper = ApiHelper.build(apiConfiguration);
        if(apiHelper == null){
            return null;
        }
        return  new GetuiPushApiInstance(apiHelper,logUrl,xmChannelId,oppoChannelId);
    }

}
