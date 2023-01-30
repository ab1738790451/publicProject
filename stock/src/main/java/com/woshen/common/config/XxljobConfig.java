package com.woshen.common.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/1/13 17:12
 * @Version: 1.0.0
 * @Description: 描述
 */
@Configuration
public class XxljobConfig {

    @Value("${xxljob.appName}")
    private String appName;
    @Value("${xxljob.admin.address}")
    private String adminAddress;
    @Value("${xxljob.executor.ip}")
    private String ipAddress;
    @Value("${xxljob.executor.port}")
    private int port;
    @Value("${xxljob.accessToken:}")
    private String accessToken;
    @Value("${xxljob.logPath}")
    private String logPath;
    @Value("${xxljob.logRetentionDays:7}")
    private int logRetentionDays;

    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor(ApplicationContext applicationContext){
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        executor.setApplicationContext(applicationContext);
        executor.setAddress(ipAddress);
        executor.setAccessToken(accessToken);
        executor.setAdminAddresses(adminAddress);
        executor.setAppname(appName);
        executor.setPort(port);
        executor.setLogPath(logPath);
        executor.setLogRetentionDays(logRetentionDays);
        return executor;
    }
}
