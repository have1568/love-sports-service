package com.love.sports.user.service;


import com.love.sports.user.entity.domain.SysClientDetail;
import com.love.sports.user.repo.SysClientDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class SysClientDetailService {

    @Resource
    private SysClientDetailRepository oauthClientDetailRepository;

    public SysClientDetail getById(String clientId) {
        Optional<SysClientDetail> optional = oauthClientDetailRepository.findById(clientId);
        return optional.orElse(null);
    }

    @Transactional
    public SysClientDetail save(SysClientDetail client) {
        return oauthClientDetailRepository.save(client);
    }
}
