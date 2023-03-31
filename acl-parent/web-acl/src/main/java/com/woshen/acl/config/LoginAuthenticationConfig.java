package com.woshen.acl.config;

import com.woshen.common.springweb.handler.AuthenticationHandle;
import com.woshen.common.webcommon.config.AuthenticationConfig;
import com.woshen.common.webcommon.utils.BaseConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/2/9 21:42
*@version 1.0
*@description
*/
@Component
public class LoginAuthenticationConfig {

    @Bean
    public AuthenticationConfig authenticationConfig(){
        AuthenticationConfig authenticationConfig = new AuthenticationConfig();
        authenticationConfig.enableLoginAuthentication(true);
        authenticationConfig.setTokenName(BaseConfigUtils.getProperty("web.acl.token","acl_token"));
        authenticationConfig.setTokenCacheKey(BaseConfigUtils.getProperty("web.acl.token.cacheKey","acl_token"));
        authenticationConfig.enableLoginRedirect(true);
        authenticationConfig.setCookieTTl(Integer.valueOf(BaseConfigUtils.getProperty("web.acl.tokenTTl","1800")));
        authenticationConfig.setCookieDomin(BaseConfigUtils.getProperty("web.acl.cookie.domain",".acl.woshen.com"));
        authenticationConfig.setExcludeUrls("/acl/**");
        return authenticationConfig;
    }

    @Bean
    public AuthenticationHandle authenticationHandle(AuthenticationConfig authenticationConfig){
          return new AuthenticationHandle(authenticationConfig);
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine springTemplateEngine){
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        Map<String,String> params = new HashMap<>();
        params.put("BASE_ADMIN_DOMAIN","www-test.acl.woshen.com");
        thymeleafViewResolver.setStaticVariables(params);
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        return thymeleafViewResolver;
    }
}
