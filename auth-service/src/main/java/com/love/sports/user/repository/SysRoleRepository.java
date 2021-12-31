package com.love.sports.user.repository;

import com.love.sports.user.entity.model.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色表持久层
 *
 * @author makejava
 * @since 2021-12-05 17:48:53
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Integer> {

    Page<SysRole> findByRoleLevelGreaterThanEqual(Integer roleLevel, Pageable pageable);

    SysRole findByRoleKey(String defaultAdmin);
}
