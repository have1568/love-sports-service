package com.love.sports.auth.entity.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_dept")
public class SysDept extends AuditModel {

    private static final long serialVersionUID = 5910955527106643973L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_dept_gen")
    @SequenceGenerator(name = "sys_dept_gen", sequenceName = "sys_dept_id_serial")
    @Column(name = "dept_id")
    private Integer id;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Column(name = "ancestors", length = 1000)
    private String ancestors;

    @Column(name = "name")
    private String name;

    @Column(name = "dept_sort", precision = 20)
    private int deptSort;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "email", length = 50)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SysDept sysDept = (SysDept) o;
        return id != null && Objects.equals(id, sysDept.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}