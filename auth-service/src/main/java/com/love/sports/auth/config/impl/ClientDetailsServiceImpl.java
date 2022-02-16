package com.love.sports.auth.config.impl;

import com.love.sports.auth.config.constant.RedisCacheNameConstant;
import com.love.sports.auth.entity.model.SysClientDetail;
import com.love.sports.auth.service.SysClientDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;


@Slf4j
@Configuration
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Resource
    private SysClientDetailService sysClientDetailService;

    @Cacheable(cacheNames = RedisCacheNameConstant.CLIENTS, key = "#clientId",cacheManager = "sysCacheManager")
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        Assert.hasText(clientId, "客户端ID不合法");

        SysClientDetail client = sysClientDetailService.findById(clientId);
        Assert.notNull(client, "客户端不存在");

        BaseClientDetails details = new BaseClientDetails();

        details.setClientId(client.getClientId());
        details.setClientSecret(client.getClientSecret());
        details.setScope(Arrays.asList(client.getScope().split(",")));
        details.setResourceIds(Arrays.asList(client.getResourceIds().split(",")));
        details.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorizedGrantTypes().split(",")));
        details.setRegisteredRedirectUri(Arrays.stream(client.getWebServerRedirectUri().split(",")).collect(Collectors.toSet()));
        details.setAutoApproveScopes(Arrays.stream(client.getAutoApprove().split(",")).collect(Collectors.toSet()));
        details.setAuthorities(AuthorityUtils.createAuthorityList(client.getAuthorities().split(",")));
        details.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
        details.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
        details.setAdditionalInformation(client.getAdditionalInformation());
        return details;
    }
}
