package com.love.sports.user.repository;


import java.lang.String;

import com.love.sports.user.entity.model.SysClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * 持久层
 *
 * @author have1568
 * @since 2021-12-06 19:55:19
 */
@Repository
public interface SysClientDetailRepository extends JpaRepository<SysClientDetail, String>, QuerydslPredicateExecutor<SysClientDetail> {
}
