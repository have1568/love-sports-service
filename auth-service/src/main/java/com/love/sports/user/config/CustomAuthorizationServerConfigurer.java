package com.love.sports.user.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Log4j2
@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Resource
    private CustomClientDetailsService customClientDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private PasswordEncoder passwordEncoder;



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
        //oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')").checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder);

    }
}
