package com.woshen.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/1/28 19:28
*@version 1.0
*@description
*/
@Configuration
public class MybatiesPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


}
