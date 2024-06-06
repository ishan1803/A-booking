package com.AuthService.config;

import com.AuthService.entity.User;
import com.AuthService.repository.UserRepository;
import com.AuthService.service.implementation.UserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AuthService {
    final JwtService jwtService;
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final private UserServiceImplementation userService;

    public AuthService(JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository, UserServiceImplementation userService) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Mono<ResponseEntity<String>> login(String email, String password) {
        System.out.println("login Executed Inside AuthService");

        Optional<User> userOptional = userService.getUser(email);

        return userOptional.map(user -> {
            if (user.getUsername().equals(email) && passwordEncoder.matches(password, user.getPassword())) {
                return Mono.just(ResponseEntity.ok(jwtService.generate(user.getUsername(), user.getId(),user.getRoles())));
            } else {
                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Credentials"));
            }
        }).orElse(Mono.error(new RuntimeException("User not registered")));
    }

}