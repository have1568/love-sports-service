package com.love.sports.user.entity.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_user_info")
public class SysUserInfo extends CommonModel {

    private static final long serialVersionUID = 1281020432067260401L;

    @Id
    @GeneratedValue(generator = "system_uuid")
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @Column(name = "user_id", nullable = false, length = 36)
    private String id;

    @Column(name = "username", length = 200)
    private String username;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "nick_name", length = 300)
    private String nickName;

    @Column(name = "email", length = 300)
    private String email;

    @Column(name = "phone_number", length = 300)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "avatar_path", length = 300)
    private String avatarPath;

    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "用户角色")
    @JoinTable(name = "sys_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    @ToString.Exclude
    private Set<SysRole> roles;

    @OneToOne
    @JoinColumn(name = "dept_id")
    @ApiModelProperty(value = "用户部门")
    private SysDept dept;


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