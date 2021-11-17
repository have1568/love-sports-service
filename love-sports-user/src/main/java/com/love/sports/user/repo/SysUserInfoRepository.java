package com.love.sports.user.repo;

import com.love.sports.user.entity.domain.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserInfoRepository extends JpaRepository<SysUserInfo, String> {


}