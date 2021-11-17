package com.love.sports.user.config.auth;

import com.love.sports.user.entity.auth.CustomGrantedAuthority;
import com.love.sports.user.entity.domain.OauthClientDetail;
import com.love.sports.user.service.OauthClientDetailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.*;


@Service
public class CustomClientDetailsService implements ClientDetailsService {


    private static final Log LOGGER = LogFactory.getLog(CustomClientDetailsService.class);


    @Resource
    private OauthClientDetailService oauthClientDetailService;
    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        OauthClientDetail client = oauthClientDetailService.getById(clientId);

        BaseClientDetails details = new BaseClientDetails();
        if (client.getClientId() == null) {
            client.setClientId(UUID.randomUUID().toString());
        }

        //模拟拥有所有的权限
        Map<RequestMappingInfo, HandlerMethod> map = this.requestMappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> set = map.keySet();
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RequestMappingInfo info : set) {
            // springmvc的url地址，不包含项目名
            PatternsRequestCondition patternsCondition = info.getPatternsCondition();
            authorities.add(new CustomGrantedAuthority(patternsCondition.toString()));

        }
        authorities.add(new CustomGrantedAuthority("/oauth/token"));
        authorities.add(new CustomGrantedAuthority("/oauth/error"));
        authorities.add(new CustomGrantedAuthority("/oauth/confirm_access"));
        authorities.add(new CustomGrantedAuthority("/oauth/check_token"));
        authorities.add(new CustomGrantedAuthority("/oauth/authorize"));

        details.setClientId(client.getClientId());
        details.setClientSecret(client.getClientSecret());
        details.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorizedGrantTypes().split(",")));
        details.setAuthorities(authorities);
        details.setRegisteredRedirectUri(Collections.emptySet());

        LOGGER.info(details);

        return details;
    }
}
