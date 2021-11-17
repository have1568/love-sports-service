package com.love.sports.user.entity.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;

    public CustomGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
