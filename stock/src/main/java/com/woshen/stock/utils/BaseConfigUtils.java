package com.woshen.stock.utils;

import com.woshen.common.config.SpringUtils;
import org.springframework.core.env.Environment;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/16 17:13
 * @Version: 1.0.0
 * @Description: 描述
 */
public class BaseConfigUtils {
    private static Environment environment;

    static {
        environment = SpringUtils.getBean(Environment.class);
    }

    /**
     * 根据名字获取属性值
     * @param name 属性名
     * @return 属性值
     */
    public static String getProperty(String name){
        return environment.getProperty(name);
    }

    /**
     * 根据名字获取属性值，当属性不存在时使用默认值
     * @param name 属性名
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static String getProperty(String name,String defaultValue) {
        return environment.getProperty(name,defaultValue);
    }
}
