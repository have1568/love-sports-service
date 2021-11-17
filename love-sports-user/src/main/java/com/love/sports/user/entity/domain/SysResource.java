package com.love.sports.user.entity.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "sys_resources")
@Entity
@Getter
@Setter
public class SysResource implements Serializable {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Column(name = "parent_ids", length = 2000)
    private String parentIds;

    @Column(name = "res_name", length = 50)
    private String resName;

    @Column(name = "res_code", length = 50)
    private String resCode;

    @Column(name = "res_path", length = 100)
    private String resPath;

    @Column(name = "http_method", length = 50)
    private String httpMethod;

    @Column(name = "res_status")
    private Character resStatus;

    @Column(name = "res_type")
    private Character resType;

    @Column(name = "res_sort", precision = 10, scale = 2)
    private BigDecimal resSort;

    @Column(name = "create_by", length = 32)
    private String createBy;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "update_by", length = 32)
    private String updateBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "del_flag")
    private Character delFlag;

    @Column(name = "remark", length = 300)
    private String remark;

}