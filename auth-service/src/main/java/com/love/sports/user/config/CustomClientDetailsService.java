package com.love.sports.user.config;

import com.love.sports.user.entity.domain.SysClientDetail;
import com.love.sports.user.service.SysClientDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Log4j2
@Service
public class CustomClientDetailsService implements ClientDetailsService {



    @Resource
    private SysClientDetailService sysClientDetailService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        Assert.hasText(clientId,"客户端ID不合法");

        SysClientDetail client = sysClientDetailService.getById(clientId);
        Assert.notNull(client,"客户端权限");

        BaseClientDetails details = new BaseClientDetails();

        //模拟拥有所有的权限
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_TRUSTED_CLIENT"));

        details.setClientId(client.getClientId());
        details.setClientSecret(client.getClientSecret());
        details.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorizedGrantTypes().split(",")));
        details.setAuthorities(authorities);
        details.setRegisteredRedirectUri(Collections.emptySet());

        log.info(details);

        return details;
    }
}
