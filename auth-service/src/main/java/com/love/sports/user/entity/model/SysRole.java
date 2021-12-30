package com.love.sports.user.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_role")
public class SysRole extends AuditModel {
    private static final long serialVersionUID = 5997898799022717421L;

    public static final String DEFAULT_ROLE = "ROLE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    private String roleName;

    private String roleKey;

    /**
     * 角色等级用于控制 高等级的可以操作低等级的的角色（删除/创建/修改）
     */
    private Integer roleLevel;

    //mappedBy 的值对应 SysUserInfo 类 的roles 属性名称
    @OneToMany(mappedBy = "sysRole", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<SysUsersRoles> usersRoles;



    @OneToMany(mappedBy = "sysRole", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<SysRolesResources> rolesResources;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SysRole sysRole = (SysRole) o;
        return id != null && Objects.equals(id, sysRole.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}