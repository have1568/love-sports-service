package com.love.sports.auth.repository;

import com.love.sports.auth.entity.model.SysDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDeptRepository extends JpaRepository<SysDept, Integer> {
}