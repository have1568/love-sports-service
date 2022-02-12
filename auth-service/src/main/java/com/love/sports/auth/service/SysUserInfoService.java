package com.love.sports.auth.service;


import com.love.sports.auth.entity.model.SysRole;
import com.love.sports.auth.entity.model.SysUserInfo;
import com.love.sports.auth.repository.SysRoleRepository;
import com.love.sports.auth.repository.SysUserInfoRepository;
import com.love.sports.auth.entity.model.QSysUserInfo;
import com.love.sports.utils.ObjectUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
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

    @Resource
    private JPAQueryFactory jpaQuery;

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
        if (ObjectUtils.isAllFieldNull(sysUserInfo)) {
            return findByJpaQuery(page);
        } else {
            ExampleMatcher.GenericPropertyMatcher contains = ExampleMatcher.GenericPropertyMatchers.contains();
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("nickName", contains);
            return sysUserInfoRepository.findAll(Example.of(sysUserInfo, matcher), page);
        }


    }

    public Page<SysUserInfo> findByJpaQuery(Pageable page) {
        QSysUserInfo qSysUserInfo = QSysUserInfo.sysUserInfo;
        List<SysUserInfo> fetch = jpaQuery.selectFrom(qSysUserInfo).offset(page.getOffset()).limit(page.getPageSize()).orderBy(qSysUserInfo.createAt.desc()).fetch();
        long total = sysUserInfoRepository.countTotal();
        return new PageImpl<>(fetch, page, total);
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

