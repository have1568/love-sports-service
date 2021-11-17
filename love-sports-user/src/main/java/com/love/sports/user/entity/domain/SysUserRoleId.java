package com.love.sports.user.entity.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class SysUserRoleId implements Serializable {
    private static final long serialVersionUID = 4875844636847933299L;
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Column(name = "role_id", nullable = false, length = 36)
    private String roleId;

    public SysUserRoleId() {
    }

    public SysUserRoleId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SysUserRoleId entity = (SysUserRoleId) o;
        return Objects.equals(this.roleId, entity.roleId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, userId);
    }
}