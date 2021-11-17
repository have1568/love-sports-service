package com.love.sports.user.entity.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "sys_user_post")
@Entity
@Getter
@Setter
public class SysUserPost {
    @EmbeddedId
    private SysUserPostId id;
}