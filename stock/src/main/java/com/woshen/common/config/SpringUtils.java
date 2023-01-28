package com.woshen.common.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/8/30 11:10
 * @Version: 1.0.0
 * @Description: 描述
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContexts;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContexts = applicationContext;
    }

    public static Object getBean(String name){
        return applicationContexts.getBean(name);
    }

    public static <T>T getBean(Class<T> cls){
        return applicationContexts.getBean(cls);
    }

    public static <T>T getBean(String name,Class<T> cls){
        return applicationContexts.getBean(name,cls);
    }
}
