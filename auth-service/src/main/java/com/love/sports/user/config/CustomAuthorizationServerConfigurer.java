package com.love.sports.user.config;

import com.love.sports.user.config.impl.CustomClientDetailsService;
import com.love.sports.user.config.impl.JdbcCacheTokenStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private CustomClientDetailsService customClientDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JdbcCacheTokenStore jdbcCacheTokenStore;

    @Resource
    private RedisTokenStore redisTokenStore;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(redisTokenStore)
//                .tokenEnhancer((accessToken, authentication) -> {
//                    if (accessToken instanceof DefaultOAuth2AccessToken) {
//                        Map<String, Object> info = new HashMap<>();
//                        info.put("principal", authentication.getPrincipal());
//                        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
//                    }
//                    return accessToken;
//                })
                .authenticationManager(authenticationManager);
        // 自定义认证异常处理类
        // .exceptionTranslator(webResponseExceptionTranslator());

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder);

    }
}
