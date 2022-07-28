package com.cinemabooking.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

//TODO: add roles
//@Entity
//@Table(name = "role")
public class Role {

    @Id
    private int id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees;

    public Role() {
    }

    public Role(int id) {
        this.id = id;
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

//    @Override
//    public String getAuthority() {
//        return getName();
//    }
}
