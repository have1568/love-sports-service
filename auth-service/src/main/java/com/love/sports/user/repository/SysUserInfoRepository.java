package com.love.sports.user.repository;

import com.love.sports.user.entity.model.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserInfoRepository extends JpaRepository<SysUserInfo, String> {


    List<SysUserInfo> findByUsernameAndIsDeleted(String username, boolean b);
}