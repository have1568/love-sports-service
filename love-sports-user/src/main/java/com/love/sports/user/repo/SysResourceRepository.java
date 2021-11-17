package com.love.sports.user.repo;

import com.love.sports.user.entity.domain.SysResource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysResourceRepository extends PagingAndSortingRepository<SysResource, String> {


}