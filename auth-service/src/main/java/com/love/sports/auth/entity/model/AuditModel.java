package com.love.sports.auth.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditModel implements Serializable {

    private static final long serialVersionUID = -9140976117275517006L;

    @CreatedBy
    @ApiModelProperty(value = "创建人")
    protected String createBy;

    @CreatedDate
    @ApiModelProperty(value = "创建时间")
    protected LocalDateTime createAt;

    @LastModifiedBy
    @ApiModelProperty(value = "更新人")
    protected String updateBy;

    @LastModifiedDate
    @ApiModelProperty(value = "更新时间")
    protected LocalDateTime updateAt;

    @ApiModelProperty(value = "是否被删除")
    protected Boolean delFlag = false; //默认值为false

    @Version
    @ApiModelProperty(value = "数据版本，用于控制并发")
    @Column(name = "data_version")
    @JsonIgnore
    protected Integer version;

    @Column(name = "data_status")
    @ApiModelProperty(value = "数据状态")
    @Enumerated(EnumType.STRING)
    protected Status status;


    public enum Status {
        INIT,
        ACTIVE,
        LOCK,
        DISABLE;
    }
}
