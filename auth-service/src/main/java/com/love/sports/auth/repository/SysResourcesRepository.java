package com.love.sports.auth.repository;


import com.love.sports.auth.entity.model.SysResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 持久层
 *
 * @author makejava
 * @since 2021-12-05 17:56:05
 */
@Repository
public interface SysResourcesRepository extends JpaRepository<SysResources, Integer> {

    /**
     * 获取用户的菜单列表
     *
     * @param userId 用户ID
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT sre.*, srr.* FROM sys_resources sre LEFT JOIN sys_roles_resources_relation srr ON sre.resource_id = srr.resource_id JOIN sys_users_roles_relation sur ON srr.role_id = sur.role_id WHERE sur.user_id = ?1 AND sre.del_flag = '0' AND sre.res_type = 'MENU' AND sre.data_status = 'ACTIVE'")
    Set<SysResources> findUserMenus(String userId);

}
