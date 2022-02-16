package com.love.sports.outs;

import com.love.sports.utils.TreeModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class ResourcesOutput implements GrantedAuthority, Output, Comparable<ResourcesOutput>, TreeModel {

    private static final long serialVersionUID = 859425614491827538L;
    private Integer id;

    private Integer parentId;

    private String resName;

    private String resIcon;

    private String resPath;

    private String httpMethod;

    private String resType;

    private Boolean root;

    private Integer resSort;

    private String clientId;
    // 子菜单
    private Collection<TreeModel> children;

    @Override
    public int compareTo(ResourcesOutput o) {
        return Integer.compare(this.getResSort(), o.getResSort());
    }

    @Override
    public String getAuthority() {
        return this.getResPath();
    }

    @Override
    public <T extends TreeModel> void setChildren(Collection<T> children) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.addAll(children);
    }

    @Override
    public Number getSelfId() {
        return this.getId();
    }

    @Override
    public Number getSelfParentId() {
        return this.getParentId();
    }

    @Override
    public boolean getSelfRoot() {
        return this.getRoot();
    }
}
