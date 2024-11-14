package com.example.camerashop_be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int idRole;
    @Column(name = "name_role")
    private String nameRole;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_role"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    List<User> listUsers;
}