package com.sven.oidc.client.config;

import org.mitre.openid.connect.web.UserInfoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig
{
    
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        
        return new WebMvcConfigurerAdapter() {
            
            @Override
            public void addInterceptors(InterceptorRegistry registry)
            {
                super.addInterceptors(registry);
                registry.addInterceptor(new UserInfoInterceptor());
            }
        };
    }
}
