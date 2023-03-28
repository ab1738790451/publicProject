package com.woshen.stock.config;

import com.woshen.common.webcommon.config.AuthenticationConfig;
import com.woshen.common.webcommon.utils.BaseConfigUtils;
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
        authenticationConfig.setTokenName(BaseConfigUtils.getProperty("web.acl.token","acl_token"));
        authenticationConfig.setTokenCacheKey(BaseConfigUtils.getProperty("web.acl.token.cacheKey","acl_token"));
        authenticationConfig.enableLoginRedirect(true);
        authenticationConfig.setCookieTTl(Integer.valueOf(BaseConfigUtils.getProperty("web.acl.tokenTTl","1800")));
        authenticationConfig.setCookieDomin(BaseConfigUtils.getProperty("web.acl.cookie.domain",".acl.woshen.com"));
        authenticationConfig.setExcludeUrls("/test/**");
        return authenticationConfig;
    }

}
