package com.love.sports.user.entity.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Table(name = "sys_role")
@Entity
@Getter
@Setter
public class SysRole {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "role_name", length = 200)
    private String roleName;

    @Column(name = "role_key", length = 200)
    private String roleKey;

    @Column(name = "role_status")
    private Character roleStatus;

    @Column(name = "data_scope")
    private Character dataScope;

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