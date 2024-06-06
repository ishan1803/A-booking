package com.AuthService.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "roles")
@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name ="role_name", unique = true)
    private RoleType role;

}