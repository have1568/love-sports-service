package com.love.sports.user.service;


import com.love.sports.user.entity.domain.OauthClientDetail;
import com.love.sports.user.repo.OauthClientDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class OauthClientDetailService {

    @Resource
    private OauthClientDetailRepository oauthClientDetailRepository;

    public OauthClientDetail getById(String clientId) {
        Optional<OauthClientDetail> optional = oauthClientDetailRepository.findById(clientId);
        return optional.orElse(null);
    }

    @Transactional
    public OauthClientDetail save(OauthClientDetail client) {
        return oauthClientDetailRepository.save(client);
    }
}
