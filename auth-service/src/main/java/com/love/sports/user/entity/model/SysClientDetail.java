package com.love.sports.user.entity.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_client_details")
public class SysClientDetail implements Serializable {

    private static final long serialVersionUID = 7123546078053337303L;

    @Id
    private String clientId;

    private String clientName;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoApprove;

    private LocalDateTime createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SysClientDetail that = (SysClientDetail) o;
        return clientId != null && Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}