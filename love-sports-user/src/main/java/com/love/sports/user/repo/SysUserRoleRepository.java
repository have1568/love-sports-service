package com.love.sports.user.repo;

import com.love.sports.user.entity.domain.SysUserRole;
import com.love.sports.user.entity.domain.SysUserRoleId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRoleRepository extends PagingAndSortingRepository<SysUserRole, SysUserRoleId> {
}