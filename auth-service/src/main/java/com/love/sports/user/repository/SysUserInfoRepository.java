package com.love.sports.user.repository;

import com.love.sports.user.entity.model.SysRole;
import com.love.sports.user.entity.model.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserInfoRepository extends JpaRepository<SysUserInfo, String> , QuerydslPredicateExecutor<SysUserInfo> {

    List<SysUserInfo> findByUsernameAndIsDeleted(String username,boolean isDeleted);
}