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
            throw new UsernameNotFoundException("???????????????:" + username);
        } else {
            sysUser = userInfos.get(0);
        }

        //?????????????????????KEY?????????PATH ???????????????
        Set<GrantedAuthority> authorities = new HashSet<>();


        //????????????????????????
        if (sysUser.getRoles().isEmpty()) {
            authorities.add(new GrantedAuthorityOut(SysRole.DEFAULT_ROLE));
        } else {
            //??????????????????????????????
            sysUser.getRoles()
                    .forEach(role -> {
                        //????????????
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
     * ?????????????????????????????????
     *
     * @param userRoles ?????????????????????
     * @return ????????????????????????????????????????????????????????????????????????
     */
    private Integer getUserRoleLevel(Set<SysRole> userRoles) {
        return userRoles.stream().min(Comparator.comparing(SysRole::getRoleLevel)).get().getRoleLevel();

    }
}
