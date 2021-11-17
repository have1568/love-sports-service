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
public class SysRoleDeptId implements Serializable {
    private static final long serialVersionUID = 580214236550844633L;
    @Column(name = "role_id", nullable = false, length = 36)
    private String roleId;
    @Column(name = "dept_id", nullable = false, length = 36)
    private String deptId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SysRoleDeptId entity = (SysRoleDeptId) o;
        return Objects.equals(this.roleId, entity.roleId) &&
                Objects.equals(this.deptId, entity.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, deptId);
    }
}
