package com.AuthService.dto;

import com.AuthService.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private long id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private Set<Role> roles;

}