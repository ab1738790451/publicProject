package com.example.demo.redis.config;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/2/6 16:04
 * @Version: 1.0.0
 * @Description: 描述
 */

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "${nacos.server-addr}",namespace = "${nacos.namespace}"))
@NacosPropertySource(dataId = "${application.name}",groupId = "${nacos.groupId}",autoRefreshed = true,type = ConfigType.PROPERTIES)
public class NacosConfig {

}
