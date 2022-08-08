package com.example.demo.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/12/29 14:48
 * @Version: 1.0.0
 * @Description: 描述
 */
public class SelfWebInterceptor implements WebRequestInterceptor {

    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        System.err.println("跑这了:"+webRequest.getContextPath());
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {
        System.err.println("postHandle:"+webRequest.getContextPath());
    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {

    }


}
