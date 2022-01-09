package com.love.sports.auth.entity.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_user_info")
public class SysUserInfo extends AuditModel {

    private static final long serialVersionUID = 1281020432067260401L;

    @Id
    @Column(name = "user_id")
    private String id;

    @NotEmpty(message = "账号不能为空")
    @Column(name = "username", length = 200)
    private String username;


    @NotEmpty(message = "密码不能为空")
    @Column(name = "password", length = 200)
    private String password;

    @NotEmpty(message = "用户昵称不能为空")
    @Column(name = "nick_name", length = 300)
    private String nickName;

    @NotEmpty(message = "邮箱不能为空")
    @Column(name = "email", length = 300)
    private String email;

    @NotEmpty(message = "手机号不能为空")
    @Column(name = "phone_number", length = 300)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "avatar_path", length = 300)
    private String avatarPath;

    //关联设置 一对多 mappedBy:是指有谁维护关联关系 设置的是关联对象的属性名
    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "用户角色")
    @JoinTable(name = "sys_users_roles_relation",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<SysRole> roles;

    @OneToOne
    @JoinColumn(name = "dept_id")
    @ApiModelProperty(value = "用户部门")
    private SysDept dept;//部门字段需要指定外键指向部门表


    public enum Sex {
        MALE,
        FEMALE,
        UNKNOWN
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SysUserInfo userInfo = (SysUserInfo) o;
        return id != null && Objects.equals(id, userInfo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}