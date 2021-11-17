package com.love.sports.user.entity.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "sys_user_role")
@Entity
@Getter
@Setter
public class SysUserRole {
    @EmbeddedId
    private SysUserRoleId id;

    public SysUserRole() {
    }

    public SysUserRole(SysUserRoleId id) {
        this.id = id;
    }
}