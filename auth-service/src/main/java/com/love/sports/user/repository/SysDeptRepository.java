package com.love.sports.user.repository;

import com.love.sports.user.entity.model.SysDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
    public interface SysDeptRepository extends JpaRepository<SysDept, Integer>, QuerydslPredicateExecutor<SysDept> {
}