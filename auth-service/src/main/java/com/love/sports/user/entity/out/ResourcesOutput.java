package com.love.sports.user.entity.out;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class ResourcesOutput implements GrantedAuthority, Output, Comparable<ResourcesOutput> {

    // 菜单id
    private int id;
    // 菜单名称
    private String name;
    // 父菜单id
    private int parentId;
    // 菜单url
    private String url;
    // 菜单图标
    private String icon;
    // 菜单顺序
    private int order;
    // 子菜单
    private Collection<ResourcesOutput> children;

    @Override
    public int compareTo(ResourcesOutput o) {
        return Integer.compare(this.getOrder(), o.getOrder());
    }

    @Override
    public String getAuthority() {
        return this.url;
    }
}
