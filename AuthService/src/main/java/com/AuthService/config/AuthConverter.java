package com.AuthService.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Log4j2
public class AuthConverter
        implements ServerAuthenticationConverter {

    @Autowired
    private JwtService jwtService;


    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        System.out.println("AuthConverter Executed Inside AuthConverter");

        String jwtToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        //String userId;
        Long userId;

        if(jwtToken != null) {
            jwtToken = jwtToken.substring(7);
            //userId = jwtService.getUserID(jwtToken);
            userId = jwtService.extractUserId(jwtToken);
        } else {
            //userId = null;
            userId = null;
        }

        //jwtService.getUserIdFromCliams(jwtToken);
        //System.out.println("User Id -> " + userId);
        System.out.println("User Id -> " + userId);

        exchange.getRequest().mutate().headers(headers -> headers.add("UserId", String.valueOf(userId)));

        return Mono.justOrEmpty(
                        exchange.getRequest()
                                .getHeaders()
                                .getFirst(HttpHeaders.AUTHORIZATION)
                )
                .filter(s -> s.startsWith("Bearer "))
                .map(s -> s.substring(7))
                .map(s -> new BearerToken(s));
    }
}