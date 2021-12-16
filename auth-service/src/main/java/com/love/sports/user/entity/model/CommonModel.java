package com.love.sports.user.entity.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonModel implements Serializable {

    private static final long serialVersionUID = -9140976117275517006L;
    @ApiModelProperty()
    @Column(name = "create_by", length = 36)
    @CreatedBy
    protected String createBy;

    @CreatedDate
    @Column(name = "create_at")
    protected LocalDateTime createAt;

    @LastModifiedBy
    @Column(name = "update_by", length = 36)
    protected String updateBy;

    @LastModifiedDate
    @Column(name = "update_at")
    protected LocalDateTime updateAt;

    @Column(name = "is_deleted")
    protected boolean isDeleted = false;

    @Version
    @Column(name = "data_version")
    protected Integer version;

    @Column(name = "remark", length = 300)
    protected String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_status")
    protected Status status = Status.ACTIVE;


    public enum Status {
        INIT(0, "初始化"),
        ACTIVE(1, "正常"),
        LOCK(2, "锁定"),
        DISABLE(2, "不可用");


        Status(int code, String des) {
            this.code = code;
            this.des = des;
        }

        private int code;

        private String des;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
