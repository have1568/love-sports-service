package com.love.sports.user.repo;

import com.love.sports.user.entity.domain.OauthClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OauthClientDetailRepository extends JpaRepository<OauthClientDetail, String> {
}