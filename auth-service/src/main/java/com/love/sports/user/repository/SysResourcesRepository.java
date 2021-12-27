package com.love.sports.user.repository;


import com.love.sports.user.entity.model.SysResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 持久层
 *
 * @author makejava
 * @since 2021-12-05 17:56:05
 */
@Repository
public interface SysResourcesRepository extends JpaRepository<SysResources, Integer> {

    @Query(value = "SELECT res.id AS value,res.resName AS text FROM SysResources res WHERE res.isDeleted = false AND res.status = 'ACTIVE'")
    List<Map<String, Object>> findAllForSelect();
}
