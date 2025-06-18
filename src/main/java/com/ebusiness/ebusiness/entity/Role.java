package com.ebusiness.ebusiness.entity;
import jakarta.persistence.*;


@Table(name = "role")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleID;

    @Column(name = "name")
    private String name;

    public Role() {}

    public Role(String name) {
        setName(name);
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
