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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.annotation.Resource;

/**
 * @author lovew
 */
@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
public class CustomWebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Resource
    private RedisIndexedSessionRepository sessionRepository;

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.maximumSessions(1).sessionRegistry(sessionRegistry()));

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
                        "/oauth/token",
                        "/oauth/confirm_access",
                        "/oauth/authorize").permitAll().anyRequest().authenticated()
                //禁用跨站伪造
                .and().httpBasic(Customizer.withDefaults())
                .csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }


}
