package com.AuthService.service;

import com.AuthService.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUser(String email);

}
