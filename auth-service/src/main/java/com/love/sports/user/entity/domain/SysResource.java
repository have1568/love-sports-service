package com.love.sports.user.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Table(name = "sys_resources")
@Entity
@Getter
@Setter
@ToString
public class SysResource extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id", nullable = false, length = 36)
    private int id;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Column(name = "parent_ids", length = 2000)
    private String parentIds;

    @Column(name = "res_name", length = 50)
    private String resName;

    @Column(name = "res_icon", length = 50)
    private String resIcon;

    @Column(name = "res_code", length = 50)
    private String resCode;

    @Column(name = "res_path", length = 100)
    private String resPath;

    @Column(name = "http_method", length = 50)
    private String httpMethod;

    @Column(name = "res_type")
    private Character resType;

    @JsonIgnore
    @ManyToMany(mappedBy = "resources")
    @ApiModelProperty(value = "菜单角色")
    @ToString.Exclude
    private Set<SysRole> roles;

    @Column(name = "res_sort", precision = 10, scale = 2)
    private int resSort;

}