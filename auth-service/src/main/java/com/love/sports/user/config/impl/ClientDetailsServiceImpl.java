package com.love.sports.user.config.impl;

import com.alibaba.fastjson.JSON;
import com.love.sports.user.entity.model.SysClientDetail;
import com.love.sports.user.service.SysClientDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Configuration
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Resource
    private SysClientDetailService sysClientDetailService;

    @Cacheable(cacheNames = "client",key = "#clientId")
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        Assert.hasText(clientId, "客户端ID不合法");

        SysClientDetail client = sysClientDetailService.findById(clientId);
        Assert.notNull(client, "客户端不存在");

        BaseClientDetails details = new BaseClientDetails();

        //模拟拥有所有的权限

        details.setClientId(client.getClientId());
        details.setClientSecret(client.getClientSecret());
        details.setScope(Arrays.asList(client.getScope().split(",")));
        details.setResourceIds(Arrays.asList(client.getResourceIds().split(",")));
        details.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorizedGrantTypes().split(",")));
        details.setRegisteredRedirectUri(Arrays.stream(client.getWebServerRedirectUri().split(",")).collect(Collectors.toSet()));
        //todo
        details.setAutoApproveScopes(Arrays.asList("true"));
        details.setAuthorities(AuthorityUtils.createAuthorityList(client.getAuthorities().split(",")));
        details.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
        details.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
        //todo
        details.setAdditionalInformation(JSON.parseObject(client.getAdditionalInformation(), Map.class));
        return details;
    }
}
