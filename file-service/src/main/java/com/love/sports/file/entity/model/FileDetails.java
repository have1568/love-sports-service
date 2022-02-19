package com.love.sports.file.entity.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author WangXinzhu
 * @date 2022/2/13 14:43
 * @since 1.0
 */
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "file_details")
@AllArgsConstructor
public class FileDetails implements Serializable {

    @Id
    @Column(name = "file_id")
    private String id;
    /**
     * 文件原始名称
     */
    private String fileOriginalName;
    private String fileName;
    private String fileDownloadUri;
    private String targetLocation;
    private String extension;
    private String contentType;
    private Long fileSize;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
    private Boolean delFlag = false; //默认值为false

    @Version
    @Column(name = "data_version")
    private Integer version;

    public FileDetails() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        FileDetails that = (FileDetails) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}