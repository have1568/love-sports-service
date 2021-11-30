package com.love.sports.user.repo;

import com.love.sports.user.entity.domain.SysDept;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDeptRepository extends PagingAndSortingRepository<SysDept, String> {
}