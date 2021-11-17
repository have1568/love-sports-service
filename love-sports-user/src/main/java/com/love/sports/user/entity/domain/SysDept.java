package com.love.sports.user.entity.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Table(name = "sys_dept")
@Entity
public class SysDept {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Column(name = "ancestors", length = 1000)
    private String ancestors;

    @Column(name = "name")
    private String name;

    @Column(name = "dept_sort", precision = 20)
    private BigDecimal deptSort;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "dept_status")
    private Character deptStatus;

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