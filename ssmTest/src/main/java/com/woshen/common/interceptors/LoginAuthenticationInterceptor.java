package com.woshen.common.interceptors;

import com.woshen.common.webcommon.filter.wapper.LoginAuthenticationWapper;
import com.woshen.common.webcommon.handler.AuthenticationConfigrationHanlder;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
*@company woshen
*@author liuhaibo
*@Date 2022/3/8 23:36
*@version 1.0
*@description
*/
public class LoginAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthenticationConfigrationHanlder authenticationConfigration = LoginAuthenticationWapper.getAuthenticationConfigration();
        if(authenticationConfigration.getAuthenticationConfig().isEnableLoginRedirect() && response.getStatus() == HttpStatus.UNAUTHORIZED.value()){
            response.sendRedirect(authenticationConfigration.getAuthenticationConfig().getLoginUrl());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
