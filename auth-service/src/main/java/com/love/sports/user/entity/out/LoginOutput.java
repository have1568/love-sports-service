package com.love.sports.user.entity.out;

import com.love.sports.user.entity.model.SysResources;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Builder
public class LoginOutput implements Output, UserDetails {

    private String id;
    private String password;
    private final String username;
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
    public static List<ResourcesOutput> buildTree(List<SysResources> resources) {
        List<ResourcesOutput> root = new ArrayList<>();
        for (SysResources node : resources) {
            if (node.isRoot()) {
                List<ResourcesOutput> children = getChildren(node, resources);
                ResourcesOutput resourcesOutput = ResourcesOutput.builder()
                        .id(node.getId())
                        .name(node.getResName())
                        .parentId(node.getParentId())
                        .url(node.getResPath())
                        .icon(node.getResIcon())
                        .order(node.getResSort())
                        .children(children)
                        .build();
                root.add(resourcesOutput);
            }
        }
        Collections.sort(root);
        return root;
    }

    private static List<ResourcesOutput> getChildren(SysResources node, List<SysResources> resources) {
        List<ResourcesOutput> children = new ArrayList<>();
        for (SysResources childrenNode : resources) {
            if (!childrenNode.isRoot() && childrenNode.getParentId() == node.getId()) {
                ResourcesOutput resourcesOutput = ResourcesOutput.builder()
                        .id(node.getId())
                        .name(node.getResName())
                        .parentId(node.getParentId())
                        .url(node.getResPath())
                        .icon(node.getResIcon())
                        .order(node.getResSort())
                        .children(getChildren(node, resources))
                        .build();
                children.add(resourcesOutput);
            }
        }
        Collections.sort(children);
        return children;
    }
}
