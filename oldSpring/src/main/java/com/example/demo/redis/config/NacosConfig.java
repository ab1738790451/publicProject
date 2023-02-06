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


@Configuration
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "${application.name}",namespace = "${application.name}"))
@NacosPropertySource(dataId = "${application.name}",groupId = "${application.name}",autoRefreshed = true,type = ConfigType.PROPERTIES)
public class NacosConfig {

}
