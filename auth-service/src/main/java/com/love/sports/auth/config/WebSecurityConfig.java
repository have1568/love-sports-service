package com.love.sports.auth.config;

import com.love.sports.auth.config.constant.Whitelist;
import com.love.sports.auth.config.handler.LoginFailHandler;
import com.love.sports.auth.config.handler.LoginSuccessHandler;
import com.love.sports.auth.config.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Collections;

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
    private PersistentTokenRepository tokenRepository;

    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    @Resource
    private VerifyCodeFilter verifyCodeFilter;


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
        web.ignoring().antMatchers(Whitelist.IGNORE_LIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //会话管理配置
        http.sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry);

        //验证码过滤器
       // http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);

        //表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
        http.formLogin().loginPage(Whitelist.LOGIN_PAGE).loginProcessingUrl(Whitelist.LOGIN_PROCESSING_URL).permitAll()
                .successHandler(loginSuccessHandler)
                .failureHandler(new LoginFailHandler())
                .and().authorizeRequests()
                //允许访问,会走过滤器
                .antMatchers(Whitelist.WHITE_LIST).permitAll()
                .antMatchers(Whitelist.IGNORE_LIST).permitAll()
                .anyRequest().authenticated()

                //跨域
                .and().cors().configurationSource(corsConfigurationSource())
                //记住我功能
                .and().rememberMe().tokenValiditySeconds(AbstractRememberMeServices.TWO_WEEKS_S / 2).tokenRepository(tokenRepository).userDetailsService(userDetailsServiceImpl)
                //禁用跨站伪造
                .and().csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //加入刷新token的Provider
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(this.passwordEncoder);
    }


    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhsot:8080"));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


}
