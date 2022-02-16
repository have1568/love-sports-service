package com.love.sports.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import javax.annotation.Resource;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.application.name}")
    private String resourceName;

    private final OAuth2AuthenticationManagerImpl oauth2AuthenticationManagerImpl;

    @Resource
    private final DefaultTokenServices defaultTokenServices;

    public ResourceServerConfig(OAuth2AuthenticationManagerImpl oauth2AuthenticationManagerImpl, DefaultTokenServices defaultTokenServices) {
        this.oauth2AuthenticationManagerImpl = oauth2AuthenticationManagerImpl;
        this.defaultTokenServices = defaultTokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        resources.tokenServices(defaultTokenServices);
        resources.resourceId(resourceName);
        resources.authenticationManager(oauth2AuthenticationManagerImpl);

    }
}
