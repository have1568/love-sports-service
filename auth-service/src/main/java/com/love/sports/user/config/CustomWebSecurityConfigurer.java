package com.love.sports.user.config;

import com.alibaba.fastjson.JSON;
import com.love.sports.user.common.ExceptionType;
import com.love.sports.user.common.Res;
import com.love.sports.user.config.constant.Whitelist;
import com.love.sports.user.config.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author lovew
 */
@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                Whitelist.LOGIN_PAGE,
                "/actuator/**",
                "/css/**",
                "/js/**",
                "/images/**",
                "/webjars/**",
                "/favicon.ico"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.maximumSessions(1).sessionRegistry(sessionRegistry));

        //表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
        http.formLogin().loginPage(Whitelist.LOGIN_PAGE).loginProcessingUrl(Whitelist.LOGIN_PROCESSING_URL).permitAll()
                .defaultSuccessUrl("http://localhost:8081/oauth/authorize?client_id=love-sports-auth&scope=all&response_type=code&redirect_uri=http://localhost:8081/test")
//                .successHandler((request, response, authentication) -> {
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//
//                    PrintWriter out = response.getWriter();
//                    out.write(JSON.toJSONString(Res.success(authentication.getPrincipal())));
//                    out.flush();
//                    out.close();
//                })
                .failureHandler((request, response, exception) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

                    PrintWriter out = response.getWriter();
                    if (exception instanceof LockedException) {
                        out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "账户被锁定!")));
                    } else if (exception instanceof CredentialsExpiredException) {
                        out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "密码过期!")));
                    } else if (exception instanceof AccountExpiredException) {
                        out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "账户过期!")));
                    } else if (exception instanceof DisabledException) {
                        out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "账户被禁用!")));
                    } else if (exception instanceof BadCredentialsException) {
                        out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "用户名或者密码输入错误，请重新输入!")));
                    } else {
                        out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), ExceptionType.AUTH_ERROR.getMessage())));
                    }

                    out.flush();
                    out.close();
                })
//                .and().exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//                    PrintWriter out = response.getWriter();
//                    out.write(JSON.toJSONString(Res.error(ExceptionType.AUTH_ERROR.getCode(), "未认证，请先登录")));
//                    out.flush();
//                    out.close();
//                })
                .and().authorizeRequests()
                //允许访问,会走过滤器
                .antMatchers(
                        Whitelist.LOGIN_PAGE,
                        Whitelist.LOGIN_PROCESSING_URL,
                        Whitelist.REGISTER_PROCESSING_URL,
                        "/oauth/token",
                        "/oauth/check_token",
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
