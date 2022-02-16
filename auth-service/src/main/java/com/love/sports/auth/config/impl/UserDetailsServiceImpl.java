package com.love.sports.auth.config.impl;

import com.love.sports.auth.entity.model.AuditModel;
import com.love.sports.auth.entity.model.SysResources;
import com.love.sports.auth.entity.model.SysRole;
import com.love.sports.auth.entity.model.SysUserInfo;
import com.love.sports.auth.repository.SysResourcesRepository;
import com.love.sports.auth.repository.SysUserInfoRepository;
import com.love.sports.auth.service.SysRoleService;
import com.love.sports.outs.GrantedAuthorityOut;
import com.love.sports.outs.LoginOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;
    @Resource
    SysResourcesRepository sysResourcesRepository;

    @Resource
    private SysRoleService sysRoleService;

    @PostConstruct
    @Transactional
    public void initAdminResources() {
        SysRole adminRole = sysRoleService.getAdminRole();
        List<SysResources> allResources = sysResourcesRepository.findAll();

        adminRole.setResources(new HashSet<>(allResources));
        sysRoleService.save(adminRole);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SysUserInfo> userInfos = sysUserInfoRepository.findByUsernameAndDelFlag(username, false);
        SysUserInfo sysUser;

        if (userInfos.isEmpty()) {
            throw new UsernameNotFoundException("用户不存在:" + username);
        } else {
            sysUser = userInfos.get(0);
        }

        //构建只包含角色KEY和资源PATH 的权限集合
        Set<GrantedAuthority> authorities = new HashSet<>();


        //如果为空默认角色
        if (sysUser.getRoles().isEmpty()) {
            authorities.add(new GrantedAuthorityOut(SysRole.DEFAULT_ROLE));
        } else {
            //获取到用户对应的资源
            sysUser.getRoles()
                    .forEach(role -> {
                        //设置角色
                        authorities.add((new GrantedAuthorityOut(role.getRoleKey())));
                        role.getResources().forEach(resource -> {
                            authorities.add(new GrantedAuthorityOut(resource.getResPath(), resource.getClientId()));
                        });
                    });

        }
        return LoginOutput.builder()
                .id(sysUser.getId())
                .username(sysUser.getUsername())
                .nickName(sysUser.getNickName())
                .roleLevel(getUserRoleLevel(sysUser.getRoles()))
                .password(sysUser.getPassword())
                .enabled(sysUser.getStatus() == AuditModel.Status.ACTIVE)
                .accountNonExpired(sysUser.getStatus() == AuditModel.Status.ACTIVE)
                .credentialsNonExpired(sysUser.getStatus() == AuditModel.Status.ACTIVE)
                .accountNonLocked(sysUser.getStatus() != AuditModel.Status.LOCK)
                .authorities(authorities)
                .build();
    }

    /**
     * 获取用户的最高角色等级
     *
     * @param userRoles 用户的多个角色
     * @return 返回用户的最高角色等级（数字越小，角色等级越高）
     */
    private Integer getUserRoleLevel(Set<SysRole> userRoles) {
        return userRoles.stream().min(Comparator.comparing(SysRole::getRoleLevel)).get().getRoleLevel();

    }
}
