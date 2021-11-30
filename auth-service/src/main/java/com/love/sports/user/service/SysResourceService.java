package com.love.sports.user.service;

import com.love.sports.user.entity.domain.SysResource;
import com.love.sports.user.repo.SysResourceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysResourceService {

    @Resource
    private SysResourceRepository sysResourceRepository;

    public List<SysResource> getUserResources(String userId) {


        return null;
    }

}
