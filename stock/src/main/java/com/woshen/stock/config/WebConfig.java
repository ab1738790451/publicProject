package com.woshen.stock.config;

import com.woshen.acl.common.interceptors.AclAuthInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: liuhaibo
 * @Date: 2023/3/29 15:57
 * @Version: 1.0.0
 * @Description: 描述
 */
@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new AclAuthInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/static/**","/test/**","/index");
    }
}
