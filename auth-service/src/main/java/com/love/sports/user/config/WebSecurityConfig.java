package com.love.sports.user.config;

import com.love.sports.user.config.constant.Whitelist;
import com.love.sports.user.config.handler.LoginFailHandler;
import com.love.sports.user.config.handler.LoginSuccessHandler;
import com.love.sports.user.config.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.Resource;

/**
 * @author lovew
 */
@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    public PersistentTokenRepository tokenRepository;

    /**
     * 用于解决 refresh_token 报错的问题
     */
    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsServiceImpl));
        return preAuthenticatedAuthenticationProvider;
    }

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
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailHandler())
                .and().authorizeRequests()
                //允许访问,会走过滤器
                .antMatchers(Whitelist.WHITE_LIST).permitAll()
                .anyRequest().authenticated()

                //需要开启，前后端分离的情况下访问接口
                .and().httpBasic(Customizer.withDefaults())

                //记住我功能
                .rememberMe().tokenValiditySeconds(AbstractRememberMeServices.TWO_WEEKS_S / 2).tokenRepository(tokenRepository).userDetailsService(userDetailsServiceImpl)
                //禁用跨站伪造
                .and().csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //加入自定义Provider
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(this.passwordEncoder);
    }


}
