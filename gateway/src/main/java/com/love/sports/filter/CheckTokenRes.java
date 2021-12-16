package com.love.sports.filter;

import java.util.Set;

public class CheckTokenRes {

    private boolean active;

    private long exp;

    private String user_name;

    private Set<String> authorities;

    private String client_id;


    private Set<String> scope;

    private String error;

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "CheckTokenRes{" +
                "active=" + active +
                ", exp=" + exp +
                ", user_name='" + user_name + '\'' +
                ", authorities=" + authorities +
                ", client_id='" + client_id + '\'' +
                ", scope=" + scope +
                '}';
    }
}
