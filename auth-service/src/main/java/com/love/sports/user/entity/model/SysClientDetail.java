package com.love.sports.user.entity.model;

import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "oauth_client_details")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysClientDetail implements Serializable {


    private static final long serialVersionUID = 7123546078053337303L;
    @Id
    @Column(name = "client_id", nullable = false, length = 256)
    private String clientId;

    @Column(name = "resource_ids", length = 256)
    private String resourceIds;

    @Column(name = "client_secret", length = 256)
    private String clientSecret;

    @Column(name = "scope", length = 256)
    private String scope;

    @Column(name = "authorized_grant_types", length = 256)
    private String authorizedGrantTypes;

    @Column(name = "web_server_redirect_uri", length = 256)
    private String webServerRedirectUri;

    @Column(name = "authorities", length = 256)
    private String authorities;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "additional_information", length = 4096)
    private String additionalInformation;

    @Column(name = "auto_approve", length = 256)
    private String autoApprove;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "is_deleted")
    protected boolean isDeleted;

}