package com.love.sports.user.entity.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class SysUserPostId implements Serializable {
    private static final long serialVersionUID = -6016285080981465815L;
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Column(name = "post_id", nullable = false, length = 36)
    private String postId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SysUserPostId entity = (SysUserPostId) o;
        return Objects.equals(this.postId, entity.postId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId);
    }
}
