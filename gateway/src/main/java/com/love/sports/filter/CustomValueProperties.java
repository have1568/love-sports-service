package com.love.sports.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * @author lovew
 */
@Component
@ConfigurationProperties(prefix = "value")
public class CustomValueProperties {

    private Set<String> whitelist;

    private String clientId;

    private String clientSecret;

    public Set<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(Set<String> whitelist) {
        this.whitelist = whitelist;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCheckTokenUrl() {
        return checkTokenUrl;
    }

    public void setCheckTokenUrl(String checkTokenUrl) {
        this.checkTokenUrl = checkTokenUrl;
    }

    private String checkTokenUrl;


}
