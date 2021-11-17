package com.love.sports.user.config.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class CustomAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {


    private static final Log LOGGER = LogFactory.getLog(CustomAuthorizationServerConfigurer.class);


    @Resource
    private CustomClientDetailsService customClientDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private DataSource dataSource;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new JdbcTokenStore(dataSource))
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService);
    }


}
