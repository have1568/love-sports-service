package com.love.sports.user.entity.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "sys_post")
@Entity
@Getter
@Setter
public class SysPost {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "post_code", length = 64)
    private String postCode;

    @Column(name = "post_name", length = 50)
    private String postName;

    @Column(name = "post_sort", precision = 10, scale = 2)
    private BigDecimal postSort;

    @Column(name = "post_status")
    private Character postStatus;

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