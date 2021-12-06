package com.love.sports.user.repository;

import com.love.sports.user.entity.model.SysClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SysClientDetailRepository extends JpaRepository<SysClientDetail, String> {
}