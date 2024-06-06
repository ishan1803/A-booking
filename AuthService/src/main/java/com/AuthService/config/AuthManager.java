package com.AuthService.config;

import com.AuthService.service.implementation.UserServiceImplementation;
import com.AuthService.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Log4j2
public class AuthManager implements ReactiveAuthenticationManager {
    final JwtService jwtService;
    final private UserServiceImplementation userService;

    public AuthManager(JwtService jwtService, UserServiceImplementation userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        System.out.println("authenticate Executed Inside AuthManager");

        return Mono.justOrEmpty(
                        authentication
                )
                .cast(BearerToken.class)
                .flatMap(auth -> {
                    String email = jwtService.getUserID(auth.getCredentials());

                    Optional<User> userOptional = userService.getUser(email);

                    Mono<User> user = Mono.justOrEmpty(userOptional.orElse(null));


                    return user.<Authentication>flatMap(u -> {
                        if (u.getUsername() == null) {
                            log.info("User not found");
                            return Mono.error(new Exception("User not found"));
                        }

                        if (jwtService.isValid(auth.getCredentials(), u.getUsername())) {
                            System.out.println("VALIDATION SUCCESSFULL");
                            return Mono.just(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));
                        }

                        log.info("Invalid / Expired Token : {}", auth.getCredentials());
                        return Mono.error(new Exception("Invalid/Expired Token"));
                    });
                });
    }

}