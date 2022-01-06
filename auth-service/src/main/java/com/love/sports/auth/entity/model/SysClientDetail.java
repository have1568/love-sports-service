package com.love.sports.auth.entity.model;

import com.alibaba.fastjson.JSON;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
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

    @Convert(converter=ClientDetailInfoConverter.class)
    private Map<String, Object> additionalInformation;

    private String autoApprove;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Column(name = "create_time")
    private LocalDateTime createAt;

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

    public enum ClientType {
        RESOURCE,
        CLIENT
    }

    static class ClientDetailInfoConverter implements AttributeConverter<Map<String,Object>,String> {

        @Override
        public String convertToDatabaseColumn(Map<String, Object> attribute) {
            return JSON.toJSONString(attribute);
        }

        @Override
        public Map<String, Object> convertToEntityAttribute(String dbData) {
            return JSON.parseObject(dbData,Map.class);
        }
    }
}