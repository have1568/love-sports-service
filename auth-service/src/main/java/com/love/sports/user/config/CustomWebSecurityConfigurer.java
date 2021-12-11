package com.love.sports.user.config;

import com.love.sports.user.config.handdler.LoginSuccessHandler;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author lovew
 */
@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(this.passwordEncoder);
    }


}
