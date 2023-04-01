package com.woshen.stock.config;

import com.woshen.common.webcommon.config.AuthenticationConfig;
import com.woshen.common.webcommon.filter.HttpBodyRewriteFilter;
import com.woshen.common.webcommon.filter.LoginAuthenticationFilter;
import com.woshen.common.webcommon.utils.BaseConfigUtils;
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
        authenticationConfig.setLoginUrl(BaseConfigUtils.getProperty("web.acl.domain","www-test.acl.woshen.com")+"/login");
        authenticationConfig.setCookieTTl(Integer.valueOf(BaseConfigUtils.getProperty("web.acl.tokenTTl","1800")));
        authenticationConfig.setCookieDomin(BaseConfigUtils.getProperty("web.acl.cookie.domain",".acl.woshen.com"));
        authenticationConfig.setExcludeUrls("/test/**");
        return authenticationConfig;
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter(){
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
        return loginAuthenticationFilter;
    }

    @Bean
    public HttpBodyRewriteFilter httpBodyRewriteFilter(){
        return new HttpBodyRewriteFilter();
    }


    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine springTemplateEngine){
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        Map<String,String> params = new HashMap<>();
        params.put("ADMIN_DOMAIN",BaseConfigUtils.getProperty("web.acl.domain","http://www-test.ac.woshen.com"));
        params.put("ADMIN_URL_PREFIX",BaseConfigUtils.getProperty("acl.url.prefix",""));
        thymeleafViewResolver.setStaticVariables(params);
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        return thymeleafViewResolver;
    }

}
