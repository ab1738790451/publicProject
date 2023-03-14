package com.woshen.acl.config;

import com.woshen.common.springweb.handler.AuthenticationHandle;
import com.woshen.common.webcommon.config.AuthenticationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
        authenticationConfig.setTokenName("ssm_token");
        authenticationConfig.setTokenCacheKey("acl_token");
        authenticationConfig.enableLoginRedirect(true);
        authenticationConfig.setCookieTTl(60*60);
        authenticationConfig.setExcludeUrls("/acl/**");
        return authenticationConfig;
    }

    @Bean
    public AuthenticationHandle authenticationHandle(AuthenticationConfig authenticationConfig){
          return new AuthenticationHandle(authenticationConfig);
    }
}
