package com.love.sports.outs;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.love.sports.utils.TreeModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

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
    private final Integer roleLevel;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private Collection<ResourcesOutput> resources;
}
