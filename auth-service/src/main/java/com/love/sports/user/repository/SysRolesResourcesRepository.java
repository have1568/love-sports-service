package com.love.sports.user.repository;

import com.love.sports.user.entity.model.SysRole;
import com.love.sports.user.entity.model.SysRolesResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SysRolesResourcesRepository extends JpaRepository<SysRolesResources, Long> {
    Set<SysRolesResources> findBySysRoleIn(Set<SysRole> roles);
}
