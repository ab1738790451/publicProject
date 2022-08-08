package com.example.demo.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/7/26 14:55
 * @Version: 1.0.0
 * @Description: 描述
 */
public class PublicThreadLocal {

    private static ThreadLocal threadLocal = new ThreadLocal();

    private PublicThreadLocal(){}

    public static void set(String key,Object value){
        Map<String, Object> localMap = (Map<String, Object>) threadLocal.get();
        if(localMap == null){
            localMap = new HashMap<>();
        }
        localMap.put(key,value);
        threadLocal.set(localMap);
    }

    public static Object get(String key){
        Map<String, Object> localMap = (Map<String, Object>) threadLocal.get();
        if(localMap == null){
            return null;
        }
       return localMap.get(key);
    }
}
