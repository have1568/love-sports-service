package com.love.sports.auth.service;


import com.love.sports.auth.entity.model.SysClientDetail;
import com.love.sports.auth.repository.SysClientDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;


/**
 * 业务层
 *
 * @author have1568
 * @since 2021-12-06 19:55:19
 */
@Slf4j
@Service
public class SysClientDetailService {

    @Resource
    private SysClientDetailRepository sysClientDetailRepository;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Transactional
    public SysClientDetail save(SysClientDetail sysClientDetail) {
        sysClientDetail.setClientSecret(passwordEncoder.encode(sysClientDetail.getClientSecret()));
        return sysClientDetailRepository.save(sysClientDetail);
    }


    @Transactional
    public boolean deleteById(String id) {
        delete(id);
        return true;
    }

    public void delete(String id) {
        sysClientDetailRepository.deleteById(id);
    }

    @Transactional
    public boolean update(SysClientDetail sysClientDetail, String id) {
        SysClientDetail saved = findById(id);
        Assert.notNull(saved, "更新数据不存在");
        Assert.notNull(sysClientDetail, "提交数据为空");
        BeanUtils.copyProperties(sysClientDetail, saved);
        sysClientDetailRepository.save(saved);
        return true;
    }

    public SysClientDetail findById(String id) {
        Assert.notNull(id, "查询Id不能为空");
        return sysClientDetailRepository.findById(id).orElse(null);
    }


    public Page<SysClientDetail> findByCondition(SysClientDetail sysClientDetail, Pageable page) {
        if (sysClientDetail == null) {
            return sysClientDetailRepository.findAll(page);
        }
        Example<SysClientDetail> example = Example.of(sysClientDetail);
        return sysClientDetailRepository.findAll(page);
    }

    public List<SysClientDetail> findAllForSelect() {
        return sysClientDetailRepository.findAll();
    }

    public List<SysClientDetail> findAllClients() {
       return sysClientDetailRepository.findByClientType(SysClientDetail.ClientType.CLIENT);
    }
}

