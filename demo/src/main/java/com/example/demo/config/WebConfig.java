package com.example.demo.config;

import com.example.demo.interceptor.SelfInterceptor;
import com.example.demo.interceptor.SelfWebInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/12/29 9:33
 * @Version: 1.0.0
 * @Description: 描述
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SelfInterceptor());
        registry.addWebRequestInterceptor(new SelfWebInterceptor());


    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
       /* registry.addMapping("/webSocket/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);*/
    }


}
