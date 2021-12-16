package com.love.sports.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
class CustomResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    //只拦截API相关接口 EnableResourceServer 先于 EnableWebSecurity 会导致 拦截或者不拦截的接口失效
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated();
    }
}