package com.example.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/8/30 11:10
 * @Version: 1.0.0
 * @Description: 描述
 */
@Configuration
public class Spring2Utils extends ApplicationObjectSupport {

    private static ApplicationContext applicationContexts;

    @Autowired
    public Spring2Utils(ApplicationContext applicationContext){
        Spring2Utils.applicationContexts = applicationContext;
    }
    public static Object getBean(String name){
        return applicationContexts.getBean(name);
    }

    public static  <T>T getBean(Class<T> cls){
        return applicationContexts.getBean(cls);
    }

    public static  <T>T getBean(String name,Class<T> cls){
        return applicationContexts.getBean(name,cls);
    }

    public static String[] getBeanNamesForType(Class<?> cls){
        return applicationContexts.getBeanNamesForType(cls);
    }
}
