package com.love.sports.outs;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class GrantedAuthorityOut implements GrantedAuthority {

    private static final long serialVersionUID = -4647945953565338717L;

    public static final int ROLE = 0;
    public static final int RESOURCE = 1;

    private String authority;

    private String clientId;

    private Integer type;

    public GrantedAuthorityOut() {
    }

    public GrantedAuthorityOut(String role) {
        this.authority = role;
        this.type = ROLE;
    }

    public GrantedAuthorityOut(String authority, String clientId) {
        this.authority = authority;
        this.clientId = clientId;
        this.type = RESOURCE;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
