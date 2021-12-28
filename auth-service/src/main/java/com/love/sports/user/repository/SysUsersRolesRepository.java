package com.love.sports.user.repository;

import com.love.sports.user.entity.model.SysUserInfo;
import com.love.sports.user.entity.model.SysUsersRoles;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SysUsersRolesRepository extends PagingAndSortingRepository<SysUsersRoles,Long> {

    Set<SysUsersRoles> findBySysUserInfo(SysUserInfo sysUserInfo);
}
