package com.love.sports.auth.repository;


import com.love.sports.auth.entity.model.SysClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 持久层
 *
 * @author have1568
 * @since 2021-12-06 19:55:19
 */
@Repository
public interface SysClientDetailRepository extends JpaRepository<SysClientDetail, String> {

    List<SysClientDetail> findByClientType(SysClientDetail.ClientType client);
}
