package com.woshen.acl.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.acl.common.constants.AclAuthKeyNs;
import com.woshen.common.redis.utils.RedisUtil;
import com.woshen.common.webcommon.model.DefaultUserModel;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.common.webcommon.utils.BaseConfigUtils;
import com.woshen.common.webcommon.utils.ThreadWebLocalUtil;
import com.woshen.common.webcommon.utils.WebUtils;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuhaibo
 * @Date: 2023/3/2 15:15
 * @Version: 1.0.0
 * @Description: 描述
 */
public class AclAuthUtils {

    private static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(5000);
        simpleClientHttpRequestFactory.setReadTimeout(5000);
        restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
    }

    /**
     * 验证当前用户是否有url访问权限
     * @param url
     * @return
     */
    public static boolean authUrlAccess(String url){
        if(StringUtils.isBlank(url)){
            return false;
        }
        DefaultUserModel user = ThreadWebLocalUtil.getUser();
        if(user != null){
            String aclDomain = BaseConfigUtils.getProperty("web.acl.domain", "43.140.209.247:18801");
            String userId = user.getUserId();
            String appId = BaseConfigUtils.getProperty("web.acl.appId");
            if(StringUtils.isBlank(appId)){
                return false;
            }
            String s = RedisUtil.hashExecutor().get(AclAuthKeyNs.ACL_USER, userId,appId);
            Map<String,Object> userMap;
            if(StringUtils.isBlank(s)){
                HashMap<String, String> queryParam = new HashMap<>();
                queryParam.put("userId",userId);
                queryParam.put("appId",appId);
                ResponseResult result = restTemplate.getForObject(aclDomain + "/acl/getUser?userId={userId}&appId={appId}", ResponseResult.class, queryParam);
                if(result.getCode() != 200){
                    return false;
                }
                userMap = (Map) result.getData();
                RedisUtil.hashExecutor().put(AclAuthKeyNs.ACL_USER, userId,appId,JSONObject.toJSONString(userMap));
            }else{
                userMap = JSONObject.parseObject(s,Map.class);
            }
            String userType = (String)userMap.get("userType");
            if("SUPER_ADMIN".equals(userType) || "ADMIN".equals(userType)){
                return true;
            }
            List roleIds1 = (List)userMap.get("roleIds");
            if(CollectionUtils.isEmpty(roleIds1)){
                return false;
            }
            String[] split = url.split("\\?");
            String uri = WebUtils.getUri(split[0]);
            String roles = RedisUtil.hashExecutor().get(AclAuthKeyNs.ACL_URL_ACCESS_ROLES, appId, uri);
            if(StringUtils.isBlank(roles)){
                boolean b = RedisUtil.hasKey(AclAuthKeyNs.ACL_URL_ACCESS_ROLES, appId);
                if(b){
                    return false;
                }
                HashMap<String, String> queryParam = new HashMap<>();
                queryParam.put("appId",appId);
                ResponseResult result = restTemplate.getForObject(aclDomain + "/acl/getUrlAccessRoles?appId={appId}", ResponseResult.class, queryParam);
                if(result.getCode() != 200){
                    return false;
                }
                Map<String,String> data = (Map<String,String>)result.getData();
                RedisUtil.hashExecutor().putAll(AclAuthKeyNs.ACL_URL_ACCESS_ROLES, appId,data);
                roles = data.get(uri);
            }
            //0 表示没有角色
            if(StringUtils.isBlank(roles) || "0".equals(roles)){
                return false;
            }
            String[] roleIds = roles.split(",");
            for (String role:roleIds
                 ) {
                 if(roleIds1.contains(role)){
                     return true;
                 }
            }
        }
        return false;
    }
}
