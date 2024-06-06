package com.userservice.UserService.service;


import com.userservice.UserService.dto.RegisterDTO;
import com.userservice.UserService.entity.UserEntity;

public interface AuthenticationService {

    public UserEntity signup(RegisterDTO input);

}
