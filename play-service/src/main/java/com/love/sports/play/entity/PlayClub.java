package com.love.sports.play.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author WangXinzhu
 * @date 2022/2/16 19:25
 * @since 1.0
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "play_club")
@NamedEntityGraph(name = "PlayClub.members", attributeNodes = @NamedAttributeNode(value = "members"))
public class PlayClub implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "play_club_id_gen")
    @SequenceGenerator(name = "play_club_id_gen", sequenceName = "play_club_id_serial", allocationSize = 1)
    @Column(name = "club_id", nullable = false)
    private Long id;

    private String clubName;

    private String clubAddress;

    @OneToOne
    private PlayUser creator;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(name = "play_user_club_relation",
            joinColumns = {@JoinColumn(name = "club_id", referencedColumnName = "club_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    private Set<PlayUser> members;


    public PlayClub() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        PlayClub playClub = (PlayClub) o;
        return id != null && Objects.equals(id, playClub.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
