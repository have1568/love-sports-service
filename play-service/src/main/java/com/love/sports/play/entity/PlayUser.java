package com.love.sports.play.entity;

import com.love.sports.outs.LoginOutput;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author WangXinzhu
 * @date 2022/2/16 19:29
 * @since 1.0
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "play_user")
@NamedEntityGraph(name = "PlayUser.clubs", attributeNodes = @NamedAttributeNode(value = "clubs"))
public class PlayUser implements Serializable {


    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "username", length = 200)
    private String username;

    @Column(name = "nick_name", length = 300)
    private String nickName;

    @ManyToMany(mappedBy = "members")
    @ToString.Exclude
    private Set<PlayClub> clubs;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        PlayUser playUser = (PlayUser) o;
        return id != null && Objects.equals(id, playUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
