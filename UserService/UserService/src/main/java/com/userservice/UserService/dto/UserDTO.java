package com.userservice.UserService.dto;


import com.userservice.UserService.entity.Role;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

// Class - Entity - UserDTO
@Data
public class UserDTO {

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private Set<Role> roles;
}