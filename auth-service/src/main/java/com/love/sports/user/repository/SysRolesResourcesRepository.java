package com.love.sports.user.repository;

import com.love.sports.user.entity.model.SysRolesResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRolesResourcesRepository extends JpaRepository<SysRolesResources, Long> {
}
