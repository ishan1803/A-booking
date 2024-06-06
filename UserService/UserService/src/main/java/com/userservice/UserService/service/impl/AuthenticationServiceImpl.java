package com.userservice.UserService.service.impl;

import com.userservice.UserService.dto.RegisterDTO;
import com.userservice.UserService.entity.Role;
import com.userservice.UserService.entity.RoleType;
import com.userservice.UserService.entity.UserEntity;
import com.userservice.UserService.exceptions.BadRequestException;
import com.userservice.UserService.repository.RoleRepository;
import com.userservice.UserService.repository.UserRepository;
import com.userservice.UserService.service.AuthenticationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@Service

public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    //Method - Signup for user
    @Override
    public UserEntity signup(RegisterDTO input) {
        logger.info("Signing up user with email: {}", input.getEmail());

        // Check if email or password is empty
        if (input.getEmail() == null || input.getEmail().isEmpty() ||
                input.getPassword() == null || input.getPassword().isEmpty()) {
            logger.error("Email or Password cannot be empty!");
            throw new BadRequestException("Email or Password Cannot be Empty!");
        }

        // Validation Check If Email Already Exists
        if (userRepository.existsByEmail(input.getEmail())) {
            logger.error("Email already exists: {}", input.getEmail());
            throw new BadRequestException("Email Already Exists!");
        }

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(input, user);
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        // Set Role For User
        RoleType roleType = RoleType.valueOf(input.getRole());
        Role role = roleRepository.findByRole(roleType).orElseThrow(() -> new RuntimeException("Role not found: " + roleType));
        user.setRoles(Collections.singleton(role));

        UserEntity savedUser = userRepository.save(user);
        logger.info("User successfully signed up with email: {}", savedUser.getEmail());
        return savedUser;
    }


}
