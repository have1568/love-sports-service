package com.love.sports.user.config;

import com.love.sports.user.service.SysUserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Log LOGGER = LogFactory.getLog(CustomClientDetailsService.class);

    @Resource
    private SysUserInfoService sysUserInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = sysUserInfoService.authUserByUsername(username);
        LOGGER.info(userDetails);
        return userDetails;

    }
}
