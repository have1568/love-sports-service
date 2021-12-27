package com.love.sports.user.repository;


import com.love.sports.user.entity.model.SysClientDetail;
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

    @Query(value = "SELECT client.clientId AS value,client.clientName AS text FROM SysClientDetail client WHERE client.isDeleted = false")
    List<Map<String, Object>> findAllForSelect();
}
