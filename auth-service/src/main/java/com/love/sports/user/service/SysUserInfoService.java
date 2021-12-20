package com.love.sports.user.service;


import com.love.sports.user.entity.model.SysUserInfo;
import com.love.sports.user.repository.SysUserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;


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
    private PasswordEncoder passwordEncoder;

    @Transactional
    public SysUserInfo save(SysUserInfo sysUserInfo) {
        sysUserInfo.setPassword(passwordEncoder.encode(sysUserInfo.getPassword()));
        return sysUserInfoRepository.save(sysUserInfo);
    }


    @Transactional
    public boolean deleteById(String id) {
        Optional<SysUserInfo> optional = sysUserInfoRepository.findById(id);
        if (optional.isPresent()) {
            SysUserInfo entity = optional.get();
            entity.setDeleted(true);
            return true;
        }
        return false;
    }

    public void delete(String id) {
        sysUserInfoRepository.deleteById(id);
    }

    @Transactional
    public boolean update(SysUserInfo sysUserInfo, String id) {
        Assert.notNull(sysUserInfo, "数据不存在");
        boolean exist = sysUserInfoRepository.existsById(id);
        if (!exist) {
            return false;
        }
        save(sysUserInfo);
        return true;
    }

    public SysUserInfo findById(String id) {
        Assert.notNull(id, "查询Id不能为空");
        return sysUserInfoRepository.findById(id).orElse(null);
    }


    public Page<SysUserInfo> findByCondition(SysUserInfo sysUserInfo, Pageable page) {

//        QSysUserInfo userInfo = QSysUserInfo.sysUserInfo;
//
//        BooleanExpression expression = userInfo.phoneNumber.eq(sysUserInfo.getPhoneNumber()).or(userInfo.username.eq(sysUserInfo.getUsername()));
//
//        sysUserInfoRepository.findAll(expression,page);

        Example<SysUserInfo> example = Example.of(sysUserInfo);
        return sysUserInfoRepository.findAll(example, page);
    }
}

