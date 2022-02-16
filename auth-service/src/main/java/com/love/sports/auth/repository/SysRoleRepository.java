package com.love.sports.auth.repository;

import com.love.sports.auth.entity.model.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 角色表持久层
 *
 * @author makejava
 * @since 2021-12-05 17:48:53
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Integer> {

    Page<SysRole> findByRoleLevelGreaterThanEqual(Integer roleLevel, Pageable pageable);

    @EntityGraph(value = "SysRole.resources")
    SysRole findByRoleKey(String defaultAdmin);


    @Override
    @EntityGraph(value = "SysRole.resources")
    Optional<SysRole> findById(Integer integer);
}
