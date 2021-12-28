package com.love.sports.user.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sys_resources")
public class SysResources extends AuditModel {

    private static final long serialVersionUID = 1053750930278613641L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Integer id;

    @Column(name = "parent_id", length = 36)
    private Integer parentId;

    @NotEmpty(message = "资源名称不能为空")
    @Column(name = "res_name", length = 50)
    private String resName;

    @Column(name = "res_icon", length = 50)
    private String resIcon;

    @Column(name = "res_path", length = 100)
    private String resPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "http_method", length = 10)
    private HttpMethod httpMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "res_type")
    private ResourceType resType;

    @Column(name = "root")
    private Boolean root;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy="sysResources",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<SysRolesResources> roleResources;

    @Transient
    @ToString.Exclude
    private Set<SysRole> roles;

    @Column(name = "res_sort")
    private Integer resSort;

    @Column(name = "client_id")
    private String clientId;


    public enum ResourceType {
        MENU,
        API
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SysResources that = (SysResources) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}