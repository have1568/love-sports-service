package com.love.sports.outs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
public class LoginOutput implements Output, UserDetails {

    private static final long serialVersionUID = 3420383551817753637L;

    private String id;
    @JsonIgnore
    private String password;
    private final String username;
    private final String nickName;

    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private Collection<ResourcesOutput> resources;


    /**
     * roleLevel 用于获取资源
     */
    private final int roleLevel;

    /**
     * 权限版本
     * 当系统修改用户，角色，资源等数据时，系统权限版本会增加1，此时用户的缓存的权限版本和系统不同，就需要重新从数据库加载
     */
    private final int permissionVersion;
}
