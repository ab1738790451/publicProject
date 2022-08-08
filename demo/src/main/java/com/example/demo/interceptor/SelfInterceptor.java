package com.example.demo.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/12/29 9:46
 * @Version: 1.0.0
 * @Description: 描述
 */
public class SelfInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        System.err.println(requestURL);
        return super.preHandle(request, response, handler);
    }
}
