package com.userservice.UserService.entity;


import jakarta.persistence.*;
import lombok.Data;

// Class - Entity - Role
@Data
@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name="role_name",unique=true)
    private RoleType role;
}