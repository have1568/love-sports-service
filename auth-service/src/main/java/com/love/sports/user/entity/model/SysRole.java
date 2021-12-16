package com.love.sports.user.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Table(name = "sys_role")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRole extends CommonModel {
    private static final long serialVersionUID = 5997898799022717421L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role_name", length = 200)
    private String roleName;

    @Column(name = "role_key", length = 200)
    private String roleKey;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles") //mappedBy 的值对应 SysUserInfo 类 的roles 属性名称
    @ApiModelProperty(value = "用户", hidden = true)
    @ToString.Exclude
    private Set<SysUserInfo> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_roles_resources",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "resource_id")})
    @ApiModelProperty(value = "菜单", hidden = true)
    @ToString.Exclude
    private Set<SysResources> resources;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SysRole sysRole = (SysRole) o;
        return Objects.equals(id, sysRole.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}