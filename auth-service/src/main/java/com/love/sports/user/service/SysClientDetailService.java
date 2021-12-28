package com.love.sports.user.service;


import com.love.sports.user.entity.model.SysClientDetail;
import com.love.sports.user.repository.SysClientDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


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
        Assert.notNull(sysClientDetail, "数据不存在");
        boolean exist = sysClientDetailRepository.existsById(id);
        if (!exist) {
            return false;
        }
        save(sysClientDetail);
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

    public List<Map<String, Object>> findAllForSelect() {
        return sysClientDetailRepository.findAllForSelect();
    }
}

