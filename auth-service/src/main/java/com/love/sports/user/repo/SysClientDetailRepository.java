package com.love.sports.user.repo;

import com.love.sports.user.entity.domain.SysClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SysClientDetailRepository extends JpaRepository<SysClientDetail, String> {
}