package com.love.sports.user.entity.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_users_roles")
@Entity
public class SysUsersRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "sys_user_info")
    private SysUserInfo sysUserInfo;

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinColumn(name = "sys_user_role")
    private SysRole sysRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SysUsersRoles that = (SysUsersRoles) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
