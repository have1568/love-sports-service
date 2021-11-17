package com.love.sports.user.entity.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Table(name = "sys_user_info")
@Entity
@Getter
@Setter
public class SysUserInfo implements Serializable {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "dept_id", length = 36)
    private String deptId;

    @Column(name = "username", length = 200)
    private String username;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "nick_name", length = 300)
    private String nickName;

    @Column(name = "user_type", length = 11)
    private String userType;

    @Column(name = "email", length = 300)
    private String email;

    @Column(name = "phone_number", length = 300)
    private String phoneNumber;

    @Column(name = "sex")
    private Character sex;

    @Column(name = "avatar_path", length = 300)
    private String avatarPath;

    @Column(name = "user_status")
    private Character userStatus;

    @Column(name = "create_by", length = 36)
    private String createBy;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "update_by", length = 36)
    private String updateBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "del_flag")
    private Character delFlag;

    @Column(name = "remark", length = 300)
    private String remark;

}