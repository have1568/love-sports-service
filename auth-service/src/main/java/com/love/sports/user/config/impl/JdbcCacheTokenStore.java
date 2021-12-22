package com.love.sports.user.config.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Collection;

@Primary
@Component
public class JdbcCacheTokenStore extends JdbcTokenStore {


    @Resource
    private RedisTokenStore redisTokenStore;


    public JdbcCacheTokenStore(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication cache = redisTokenStore.readAuthentication(token);
        return cache == null ? super.readAuthentication(token) : cache;
    }

    @Transactional
    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        super.storeAccessToken(token, authentication);
        redisTokenStore.storeAccessToken(token, authentication);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken cache = redisTokenStore.readAccessToken(tokenValue);
        return cache == null ? super.readAccessToken(tokenValue) : cache;
    }

    @Transactional
    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        redisTokenStore.removeAccessToken(token);
        super.removeAccessToken(token);
    }

    @Transactional
    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        super.storeRefreshToken(refreshToken, authentication);
        redisTokenStore.storeRefreshToken(refreshToken, authentication);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        OAuth2RefreshToken cache = redisTokenStore.readRefreshToken(tokenValue);
        return cache == null ? super.readRefreshToken(tokenValue) : cache;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
        OAuth2Authentication cache = redisTokenStore.readAuthenticationForRefreshToken(value);
        return cache == null ? super.readAuthenticationForRefreshToken(value) : cache;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        redisTokenStore.removeRefreshToken(token);
        super.removeRefreshToken(token);
    }

    @Transactional
    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        redisTokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
        super.removeAccessTokenUsingRefreshToken(refreshToken);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken cache = redisTokenStore.getAccessToken(authentication);
        return cache == null ? super.getAccessToken(authentication) : cache;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        Collection<OAuth2AccessToken> cache = redisTokenStore.findTokensByClientIdAndUserName(clientId, userName);
        return cache == null ? super.findTokensByClientIdAndUserName(clientId, userName) : cache;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        Collection<OAuth2AccessToken> cache = redisTokenStore.findTokensByClientId(clientId);
        return cache == null ? super.findTokensByClientId(clientId) : cache;
    }
}
