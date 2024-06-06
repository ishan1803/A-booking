package com.AuthService.controller;

import com.AuthService.config.AuthService;
import com.AuthService.config.JwtService;
import com.AuthService.dto.LoginUserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Log4j2
@RestController()
@RequestMapping("/auth")
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthService authService;

    public AuthenticationController(PasswordEncoder passwordEncoder, JwtService jwtService, AuthService authService) {

        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authService = authService;
    }

    public @GetMapping("/validate")
    Mono<ResponseEntity<String>> val() {
        log.info("End Point Hit: /validate ||| val() method Executed");
        return Mono.just(ResponseEntity.ok("VALIDATION END POINT HIT!"));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginUserDto loginUserDto) {
        log.info("Logging endpoint {}", loginUserDto.toString());
        return authService.login(loginUserDto.getEmail(), loginUserDto.getPassword());
    }

}