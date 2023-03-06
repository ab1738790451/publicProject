package com.woshen.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/7/12 16:47
 * @Version: 1.0.0
 * @Description: 描述
 */
@Component
public class ThymeleafConfig {

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Bean
    private void initThymelaefConfig(){
        if(thymeleafViewResolver != null){
            Map<String,Object> map = new HashMap<>();
            map.put("serviceName","测试玩");
            thymeleafViewResolver.setStaticVariables(map);
        }
    }
}
