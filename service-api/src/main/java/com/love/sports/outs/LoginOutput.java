package com.love.sports.outs;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class LoginOutput implements Output, UserDetails {

    private static final long serialVersionUID = 3420383551817753637L;

    private String id;
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;
    private final String username;
    private final String nickName;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private List<ResourcesOutput> resources;


    /**
     * 创建树
     * 没有在遍历的时候移除已经添加的元素，考虑到移除元素也有复杂度
     */
    public static List<ResourcesOutput> buildTree(List<ResourcesOutput> resources) {
        List<ResourcesOutput> root = new ArrayList<>();
        for (ResourcesOutput node : resources) {
            if (node.getRoot()) {
                List<ResourcesOutput> children = getChildren(node, resources);
                node.setChildren(children);
                root.add(node);
            }
        }
        Collections.sort(root);
        return root;
    }

    private static List<ResourcesOutput> getChildren(ResourcesOutput node, List<ResourcesOutput> resources) {
        List<ResourcesOutput> children = new ArrayList<>();
        for (ResourcesOutput childrenNode : resources) {
            if (!childrenNode.getRoot() && childrenNode.getParentId().equals(node.getId())) {
                children.add(childrenNode);
            }
        }
        Collections.sort(children);
        return children;
    }
}
