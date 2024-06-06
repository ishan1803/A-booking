package com.userservice.UserService.controller;

import com.userservice.UserService.dto.RegisterDTO;
import com.userservice.UserService.entity.UserEntity;
import com.userservice.UserService.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // Method - Signup Controller
    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterDTO registerDto) {
        UserEntity registeredUser = authenticationService.signup(registerDto);

        return ResponseEntity.ok(registeredUser);
    }

}
