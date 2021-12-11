package com.love.sports.user.config;

import com.love.sports.user.config.handdler.LoginSuccessHandler;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class CustomResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage(FromLoginConstant.LOGIN_PAGE).loginProcessingUrl(FromLoginConstant.LOGIN_PROCESSING_URL).permitAll().successHandler(new LoginSuccessHandler())
                .and().authorizeRequests()
                //允许访问
                .antMatchers(
                        FromLoginConstant.LOGIN_PAGE,
                        FromLoginConstant.LOGIN_PROCESSING_URL,
                        StaticResourceLocation.CSS.name(),
                        StaticResourceLocation.JAVA_SCRIPT.name(),
                        StaticResourceLocation.IMAGES.name(),
                        StaticResourceLocation.WEB_JARS.name(),
                        StaticResourceLocation.FAVICON.name(),
                        "/api/user/register",
                        "/oauth/token",
                        "/oauth/confirm_access",
                        "/oauth/authorize").permitAll().anyRequest().authenticated()
                .and().httpBasic(Customizer.withDefaults())
                //禁用跨站伪造
                .csrf().disable();
    }
}
