package com.love.sports.user.service;


import com.love.sports.user.entity.model.SysRole;
import com.love.sports.user.entity.model.SysUserInfo;
import com.love.sports.user.repository.SysRoleRepository;
import com.love.sports.user.repository.SysUserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 业务层
 *
 * @author makejava
 * @since 2021-12-05 17:59:18
 */
@Slf4j
@Service
public class SysUserInfoService {

    @Resource
    private SysUserInfoRepository sysUserInfoRepository;

    @Resource
    private SysRoleRepository sysRoleRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Transactional
    public SysUserInfo save(SysUserInfo sysUserInfo) {
        sysUserInfo.setPassword(passwordEncoder.encode(sysUserInfo.getPassword()));
        List<SysRole> savedRoles = getAndVerifyRoles(sysUserInfo);
        sysUserInfo.setRoles(new HashSet<>(savedRoles));
        return sysUserInfoRepository.save(sysUserInfo);
    }


    @Transactional
    public boolean deleteById(String id) {
        Optional<SysUserInfo> optional = sysUserInfoRepository.findById(id);
        if (optional.isPresent()) {
            SysUserInfo entity = optional.get();
            entity.setDelFlag(true);
            return true;
        }
        return false;
    }

    public void delete(String id) {
        sysUserInfoRepository.deleteById(id);
    }

    @Transactional
    public boolean update(SysUserInfo sysUserInfo, String id) {
        SysUserInfo saved = findById(id);
        Assert.notNull(saved, "更新数据不存在");
        Assert.notNull(sysUserInfo, "提交数据为空");
        BeanUtils.copyProperties(sysUserInfo, saved);
        List<SysRole> savedRoles = getAndVerifyRoles(sysUserInfo);
        saved.setRoles(new HashSet<>(savedRoles));
        sysUserInfoRepository.save(saved);
        return true;
    }

    public SysUserInfo findById(String id) {
        Assert.notNull(id, "查询Id不能为空");
        return sysUserInfoRepository.findById(id).orElse(null);
    }


    public Page<SysUserInfo> findByCondition(SysUserInfo sysUserInfo, Pageable page) {
        ExampleMatcher.GenericPropertyMatcher contains = ExampleMatcher.GenericPropertyMatchers.contains();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", contains)
                .withMatcher("nickName", contains);
        return sysUserInfoRepository.findAll(Example.of(sysUserInfo, matcher), page);
    }

    private List<SysRole> getAndVerifyRoles(SysUserInfo sysUserInfo) {
        Set<Integer> ids = sysUserInfo.getRoles().stream().map(SysRole::getId).collect(Collectors.toSet());
        List<SysRole> sysRoles = sysRoleRepository.findAllById(ids);

        if (sysRoles.size() < sysUserInfo.getRoles().size()) {
            throw new IllegalArgumentException("资源不存在");
        }
        return sysRoles;
    }

}

