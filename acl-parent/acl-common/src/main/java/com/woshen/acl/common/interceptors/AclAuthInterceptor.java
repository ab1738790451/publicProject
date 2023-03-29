package com.woshen.acl.common.interceptors;

import com.woshen.acl.common.utils.AclAuthUtils;
import com.woshen.common.webcommon.exception.BaseRuntimeException;
import com.woshen.common.webcommon.utils.BaseConfigUtils;
import com.woshen.common.webcommon.utils.WebUtils;
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
public class AclAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean canAccess = AclAuthUtils.authUrlAccess(request.getRequestURI());
        if(!canAccess){
            if(WebUtils.isAjax(request)){
                throw new BaseRuntimeException("您没有该路径的访问权限，拒绝访问");
            }else{
                response.setCharacterEncoding("UTF-8");
                response.sendRedirect(BaseConfigUtils.getProperty("acl.domain","") + "/unAuth");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null){
            modelAndView.addObject("ADMIN_DOMAIN", BaseConfigUtils.getProperty("acl.domain","localhost"));
            modelAndView.addObject("ADMIN_URL_PREFIX", BaseConfigUtils.getProperty("acl.url.prefix","localhost"));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
