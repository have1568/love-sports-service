package com.love.sports.auth.repository;


import com.love.sports.auth.entity.model.SysResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 持久层
 *
 * @author makejava
 * @since 2021-12-05 17:56:05
 */
@Repository
public interface SysResourcesRepository extends JpaRepository<SysResources, Integer> {

    @Query(value = "SELECT res.id AS value,res.resName AS text FROM SysResources res WHERE res.delFlag = false AND res.status = 'ACTIVE'")
    List<Map<String, Object>> findAllForSelect();

    @Query(nativeQuery = true ,value = "SELECT sre.*, srr.* FROM sys_resources sre LEFT JOIN sys_roles_resources srr ON sre.resource_id = srr.sys_resources LEFT JOIN sys_users_roles sur ON srr.sys_role = sur.sys_user_role WHERE sur.sys_user_info = ?1 AND sre.del_flag = '0'")
    Set<SysResources> findByUserId(String userId);

}
