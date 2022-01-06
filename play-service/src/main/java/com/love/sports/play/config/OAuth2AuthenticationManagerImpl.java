package com.love.sports.play.config;

import com.love.sports.outs.GrantedAuthorityOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;


@Slf4j
@Configuration
public class OAuth2AuthenticationManagerImpl extends OAuth2AuthenticationManager {

    @Resource
    private DefaultTokenServices defaultTokenServices;

    @Value("${spring.application.name}")
    private String resourceId;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication == null) {
            throw new InvalidTokenException("Invalid token (token not found)");
        }
        String token = (String) authentication.getPrincipal();
        OAuth2Authentication auth = defaultTokenServices.loadAuthentication(token);
        if (auth == null) {
            throw new InvalidTokenException("Invalid token: " + token);
        }

        Collection<String> resourceIds = auth.getOAuth2Request().getResourceIds();
        if (resourceId != null && resourceIds != null && !resourceIds.isEmpty() && !resourceIds.contains(resourceId)) {
            throw new OAuth2AccessDeniedException("Invalid token does not contain resource id (" + resourceId + ")");
        }


        //检查当前url是否在用户的权限列表里
        checkCurrentUrlIsMatch(auth);

        if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            // Guard against a cached copy of the same details
            if (!details.equals(auth.getDetails())) {
                // Preserve the authentication details from the one loaded by token services
                details.setDecodedDetails(auth.getDetails());
            }
        }
        auth.setDetails(authentication.getDetails());
        auth.setAuthenticated(true);
        return auth;

    }

    private void checkCurrentUrlIsMatch(OAuth2Authentication auth) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String currentUrl = request.getRequestURI();
        Collection<GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<GrantedAuthority> iterator = authorities.iterator();
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        boolean isMatch = false;

        while (iterator.hasNext()) {
            GrantedAuthorityOut next = (GrantedAuthorityOut) iterator.next();
            if (StringUtils.hasText(next.getClientId()) && StringUtils.hasText(next.getAuthority())) {
                //路径与路径对应的客户端都匹配才能认证成功
                if (antPathMatcher.match(next.getAuthority(), currentUrl) && next.getClientId().equals(resourceId)) {
                    isMatch = true;
                    break;
                }
            } else {
                log.warn("Bad GrantedAuthority : {}", next);
            }

        }
        if (!isMatch) {
            throw new OAuth2AccessDeniedException("Assess Invalid does not contain url (" + currentUrl + ")");
        }
    }

    @Override
    public void afterPropertiesSet() {

    }
}
