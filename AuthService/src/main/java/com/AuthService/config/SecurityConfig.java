package com.AuthService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    public @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }


    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addExposedHeader(HttpHeaders.SET_COOKIE);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(corsConfigurationSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, AuthManager jwtAuthManager, AuthConverter jwtAuthConverter, CorsConfigurationSource corsConfigurationSource) {
        System.out.println("SecurityFilterChain Executed Inside Security Config");

        AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(jwtAuthManager);
        jwtFilter.setServerAuthenticationConverter(jwtAuthConverter);

        return http
                .authorizeExchange(auth -> {
                    auth.pathMatchers(HttpMethod.GET, "/users/test").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.POST, "/users/signup").permitAll();
                    auth.pathMatchers(HttpMethod.POST, "/auth/validate").permitAll();
                    auth.pathMatchers(HttpMethod.POST, "/auth/login").permitAll();
                    auth.pathMatchers(HttpMethod.GET, "/users/test").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.POST, "/users/signup").permitAll();
                    auth.pathMatchers(HttpMethod.POST, "/auth/validate").permitAll();
                    auth.pathMatchers(HttpMethod.POST, "/auth/login").permitAll();
                    auth.pathMatchers(HttpMethod.GET, "/activities/forCustomer/*").hasRole("CUSTOMER");
                    auth.pathMatchers(HttpMethod.GET, "/activities/forAdmin/*").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.GET, "/activities/forExpert/*").hasRole("EXPERT");
                    auth.pathMatchers(HttpMethod.GET, "/activities/All").hasAnyRole("ADMIN","CUSTOMER");
                    auth.pathMatchers(HttpMethod.POST, "/activities/add").hasRole("EXPERT");
                    auth.pathMatchers(HttpMethod.PUT, "/activities/*").hasRole("EXPERT");
                    auth.pathMatchers(HttpMethod.DELETE, "/activities/*").hasRole("EXPERT");
                    auth.pathMatchers(HttpMethod.GET, "/activities/byLocation/*").hasAnyRole("ADMIN","CUSTOMER");
                    auth.pathMatchers(HttpMethod.GET, "/activities/byName/*").hasAnyRole("ADMIN","CUSTOMER");
                    auth.pathMatchers(HttpMethod.GET, "/activities/byExpert/*").hasRole("EXPERT");
                    auth.pathMatchers(HttpMethod.GET, "/activities/test").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.GET, "/users/").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.GET, "/users/forCustomer/*").hasRole("CUSTOMER");
                    auth.pathMatchers(HttpMethod.GET, "/users/forExpert/*").hasRole("EXPERT");
                    auth.pathMatchers(HttpMethod.GET, "/users/forAdmin/*").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.PUT, "/users/*").permitAll();
                    auth.pathMatchers(HttpMethod.DELETE, "/users/*").permitAll();
                    auth.pathMatchers(HttpMethod.GET, "/experiences/test").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.POST, "/experiences/{activityId}").hasRole("CUSTOMER");
                    auth.pathMatchers(HttpMethod.PUT, "/experiences/edit/*").hasRole("CUSTOMER");
                    auth.pathMatchers(HttpMethod.GET, "/experiences/all").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.GET, "/experiences/byActivityId/*").permitAll();
                    auth.pathMatchers(HttpMethod.GET, "/bookings/byUserId/*").hasAnyRole("ADMIN","CUSTOMER");
                    auth.pathMatchers(HttpMethod.DELETE, "/bookings/cancel/*").hasRole("CUSTOMER");
                    auth.pathMatchers(HttpMethod.POST, "/bookings/make/*").hasRole("CUSTOMER");
                    auth.pathMatchers(HttpMethod.GET, "/bookings/all").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.GET, "/bookings/byActivityId").hasAnyRole("ADMIN","EXPERT");
                    auth.pathMatchers(HttpMethod.GET, "/bookings/forAdmin/getById/*").hasRole("ADMIN");
                    auth.pathMatchers(HttpMethod.GET, "/bookings/forCustomer/getById/*").hasRole("CUSTOMER");
                    auth.pathMatchers(HttpMethod.GET, "/bookings/forExpert/getById/*").hasRole("EXPERT");

                    auth.anyExchange().authenticated();
                })
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource)
                .and()
                .build();
    }

}