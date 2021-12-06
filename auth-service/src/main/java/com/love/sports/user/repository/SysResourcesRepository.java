package com.love.sports.user.repository;


import java.lang.Integer;

import com.love.sports.user.entity.model.SysResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * 持久层
 *
 * @author makejava
 * @since 2021-12-05 17:56:05
 */
@Repository
public interface SysResourcesRepository extends JpaRepository<SysResources, Integer>, QuerydslPredicateExecutor<SysResources> {
}
