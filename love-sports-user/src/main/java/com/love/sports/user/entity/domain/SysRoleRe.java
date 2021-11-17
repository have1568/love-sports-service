package com.love.sports.user.entity.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "sys_role_res")
@Entity
@Getter
@Setter
public class SysRoleRe {
    @EmbeddedId
    private SysRoleReId id;
}