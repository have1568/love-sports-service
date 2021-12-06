package com.love.sports.user.entity.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "sys_dept")
@Entity
@ToString
public class SysDept extends CommonModel {

    @Id
    @Column(name = "dept_id", nullable = false, length = 36)
    private int id;

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

}