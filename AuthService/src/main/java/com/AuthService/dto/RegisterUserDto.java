package com.AuthService.dto;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String name;

    private String email;

    private String password;

    private String role;

}