package com.love.sports.play.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author WangXinzhu
 * @date 2022/2/17 19:24
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final OAuth2AuthenticationManagerImpl authenticationManagerImpl;

    public WebSecurityConfig(OAuth2AuthenticationManagerImpl authenticationManagerImpl) {
        this.authenticationManagerImpl = authenticationManagerImpl;
    }


    @Override
    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManagerBean() {
        return authenticationManagerImpl;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();

        http.authorizeRequests()
                //允许访问,会走过滤器
                .antMatchers("/auth/**").permitAll()
                //其余的都要
                .anyRequest().authenticated();

    }
}
