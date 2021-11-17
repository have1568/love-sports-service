package com.love.sports.user.service;


import com.love.sports.user.entity.auth.CustomGrantedAuthority;
import com.love.sports.user.entity.domain.SysUserInfo;
import com.love.sports.user.repo.SysUserInfoRepository;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysUserInfoService {

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public UserDetails authUserByUsername(String username) {
        SysUserInfo sysUserInfoExample = new SysUserInfo();
        sysUserInfoExample.setUsername(username);
        List<SysUserInfo> userInfos = sysUserInfoRepository.findAll(Example.of(sysUserInfoExample));

        SysUserInfo userInfo;

        if (userInfos.isEmpty()) {throw new UsernameNotFoundException("用户名不存在:" + username);

        } else {
            userInfo = userInfos.get(0);
        }
        return new User(userInfo.getUsername(), userInfo.getPassword(), true, true, true, true, getAuthorities());
    }

    private Set<GrantedAuthority> getAuthorities() {
        Set<RequestMappingInfo> set = requestMappingHandlerMapping.getHandlerMethods().keySet();
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RequestMappingInfo info : set) {
            // springmvc的url地址，不包含项目名
            PatternsRequestCondition patternsCondition = info.getPatternsCondition();
            authorities.add(new CustomGrantedAuthority(patternsCondition.toString()));

        }
        return authorities;
    }
}
