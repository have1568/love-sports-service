package com.love.sports.user.entity.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class CommonEntity implements Serializable {

    @ApiModelProperty()
    @Column(name = "create_by", length = 36)
    protected String createBy;

    @Column(name = "create_at")
    protected LocalDateTime createAt;

    @Column(name = "update_by", length = 36)
    protected String updateBy;

    @Column(name = "update_at")
    protected LocalDateTime updateAt;

    @Column(name = "is_deleted")
    protected boolean isDeleted;

    @Version
    @Column(name = "data_version")
    protected Integer version;

    @Column(name = "remark", length = 300)
    protected String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_status")
    protected Status status = Status.ACTIVE;


    enum Status {
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
