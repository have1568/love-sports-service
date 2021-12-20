package com.love.sports.outs;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class ResourcesOutput implements GrantedAuthority, Output, Comparable<ResourcesOutput> {

    private static final long serialVersionUID = 859425614491827538L;
    private Integer id;

    private Integer parentId;

    private String parentIds;

    private String resName;

    private String resIcon;

    private String resCode;

    private String resPath;

    private String httpMethod;

    private String resType;

    private Boolean root;

    private Integer resSort;

    private String clientId;
    // 子菜单
    private Collection<ResourcesOutput> children;

    @Override
    public int compareTo(ResourcesOutput o) {
        return Integer.compare(this.getResSort(), o.getResSort());
    }

    @Override
    public String getAuthority() {
        return this.getResPath();
    }
}
