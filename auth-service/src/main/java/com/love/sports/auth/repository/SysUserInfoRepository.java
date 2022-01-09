package com.love.sports.auth.repository;

import com.love.sports.auth.entity.model.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserInfoRepository extends JpaRepository<SysUserInfo, String>, QuerydslPredicateExecutor<SysUserInfo> {


    List<SysUserInfo> findByUsernameAndDelFlag(String username, boolean b);

    List<SysUserInfo> findByEmailAndDelFlag(String email, boolean b);

    List<SysUserInfo> findByPhoneNumberAndDelFlag(String phoneNumber, boolean b);

    @Query(value = "SELECT reltuples FROM pg_class CLS LEFT JOIN pg_namespace N ON (N.oid = CLS.relnamespace) WHERE nspname NOT IN ('pg_catalog', 'information_schema') AND relkind = 'r' AND relname = 'sys_user_info'", nativeQuery = true)
    long countTotal();

}