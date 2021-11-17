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
public class SysRoleReId implements Serializable {
    private static final long serialVersionUID = 5697887885202096530L;
    @Column(name = "role_id", nullable = false, length = 36)
    private String roleId;
    @Column(name = "res_id", nullable = false, length = 36)
    private String resId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SysRoleReId entity = (SysRoleReId) o;
        return Objects.equals(this.roleId, entity.roleId) &&
                Objects.equals(this.resId, entity.resId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, resId);
    }

}