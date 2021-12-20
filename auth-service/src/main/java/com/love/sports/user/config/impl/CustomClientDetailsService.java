package com.love.sports.user.config.impl;

import com.love.sports.user.entity.model.SysClientDetail;
import com.love.sports.user.service.SysClientDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CustomClientDetailsService implements ClientDetailsService {

    @Resource
    private SysClientDetailService sysClientDetailService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        Assert.hasText(clientId, "客户端ID不合法");

        SysClientDetail client = sysClientDetailService.findById(clientId);
        Assert.notNull(client, "客户端不存在");

        BaseClientDetails details = new BaseClientDetails();

        //模拟拥有所有的权限

        details.setClientId(client.getClientId());
        details.setClientSecret(client.getClientSecret());
        details.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorizedGrantTypes().split(",")));
        details.setAuthorities(AuthorityUtils.createAuthorityList(client.getAuthorities().split(",")));
        details.setScope(Arrays.asList(client.getScope().split(",")));
        details.setRegisteredRedirectUri(Arrays.stream(client.getWebServerRedirectUri().split(",")).collect(Collectors.toSet()));
        // todo
        details.setAutoApproveScopes(Arrays.asList("true"));
        details.setResourceIds(Arrays.asList(client.getResourceIds().split(",")));
        return details;
    }
}
