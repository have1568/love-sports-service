package com.love.sports.user.config;

import com.love.sports.user.config.impl.CustomClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

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
    private RedisConnectionFactory redisConnectionFactory;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .accessTokenConverter(new JwtAccessTokenConverter());
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
