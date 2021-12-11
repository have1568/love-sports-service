package com.love.sports.user.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Table(name = "sys_resources")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysResources extends CommonModel {

    private static final long serialVersionUID = 1053750930278613641L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id", nullable = false, length = 36)
    private int id;

    @Column(name = "parent_id", length = 36)
    private Integer parentId;

    @Column(name = "parent_ids", length = 2000)
    private String parentIds;

    @NotEmpty(message = "资源名称不能为空")
    @Column(name = "res_name", length = 50)
    private String resName;

    @Column(name = "res_icon", length = 50)
    private String resIcon;

    @Column(name = "res_code", length = 50)
    private String resCode;

    @Column(name = "res_path", length = 100)
    private String resPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "http_method", length = 10)
    private HttpMethod httpMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "res_type")
    private ResourceType resType;

    @Column(name = "root")
    private boolean root;

    @JsonIgnore
    @ManyToMany(mappedBy = "resources")
    @ApiModelProperty(value = "菜单角色")
    @ToString.Exclude
    private Set<SysRole> roles;

    @Column(name = "res_sort", precision = 10, scale = 2)
    private int resSort;


    public enum ResourceType {
        MENU,
        API
    }
}