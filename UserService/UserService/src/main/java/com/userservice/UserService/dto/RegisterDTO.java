package com.userservice.UserService.dto;

import lombok.Data;

// Class - Entity - RegisterDTO
@Data
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private String role;

}