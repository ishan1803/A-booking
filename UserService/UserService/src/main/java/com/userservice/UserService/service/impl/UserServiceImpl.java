package com.userservice.UserService.service.impl;

import com.userservice.UserService.dto.RegisterDTO;
import com.userservice.UserService.dto.UserDTO;
import com.userservice.UserService.entity.Role;
import com.userservice.UserService.entity.RoleType;
import com.userservice.UserService.entity.UserEntity;
import com.userservice.UserService.exceptions.BadRequestException;
import com.userservice.UserService.exceptions.ResourceNotFoundException;
import com.userservice.UserService.repository.RoleRepository;
import com.userservice.UserService.repository.UserRepository;
import com.userservice.UserService.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Class - Implementing User Service Interface
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    // Method - Implementing - Registering a User
    @Override
    public boolean createUser(RegisterDTO registerDTO) {
        logger.info("Creating user with email: {}", registerDTO.getEmail());

        if (registerDTO.getEmail() == null || registerDTO.getEmail().isEmpty() || registerDTO.getPassword() == null || registerDTO.getPassword().isEmpty()) {
            logger.error("Email or Password cannot be empty!");
            throw new BadRequestException("Email or Password cannot be empty!");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            logger.error("Email already exists: {}", registerDTO.getEmail());
            throw new BadRequestException("Email already exists!");
        }

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(registerDTO, user);
        logger.debug("User entity created: {}", user);

        try {
            RoleType roleType = RoleType.valueOf(registerDTO.getRole());
            Role role = roleRepository.findByRole(roleType).orElseThrow(() -> new RuntimeException("Role not found: " + roleType));
            user.setRoles(Collections.singleton(role));
            logger.info("Role assigned to user: {}", roleType);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid role: {}", registerDTO.getRole());
            throw new RuntimeException("Invalid role: " + registerDTO.getRole());
        }

        try {
            userRepository.save(user);
            logger.info("User successfully created with email: {}", user.getEmail());
            return true;
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage());
            return false;
        }
    }

    // Method - Implementing - Getting All User Details
    @Override
    public List<UserEntity> getAllUsers() {
        logger.info("Fetching all users");
        List<UserEntity> users = userRepository.findAll();
        logger.info("Number of users fetched: {}", users.size());
        return users;
    }

    // Method - Implementing - Getting a User by User ID
    @Override
    public UserEntity getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("User found with ID: {}", id);
        } else {
            logger.warn("User not found with ID: {}", id);
        }
        return user.orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
    }

    // Method - Implementing - Updating a User by User ID
    @Override
    public boolean updateUserById(Long id, UserDTO userDTO) {
        logger.info("Updating user with ID: {}", id);
        Optional<UserEntity> userFound = userRepository.findById(id);
        if (userFound.isEmpty()) {
            logger.warn("Cannot find user with ID: {}", id);
            throw new ResourceNotFoundException("Cannot find user with given id!");
        }

        UserEntity user = userFound.get();
        user.setName(userDTO.getName() != null ? userDTO.getName() : user.getName());
        user.setEmail(userDTO.getEmail() != null ? userDTO.getEmail() : user.getEmail());
        logger.debug("User entity updated: {}", user);

        try {
            userRepository.save(user);
            logger.info("User successfully updated with ID: {}", id);
            return true;
        } catch (Exception e) {
            logger.error("Error updating user with ID: {}", id);
            return false;
        }
    }

    // Method - Implementing - Deleting a User By User ID
    @Override
    public boolean deleteUserById(Long id) {
        logger.info("Deleting user with ID: {}", id);
        Optional<UserEntity> userFound = userRepository.findById(id);
        if (userFound.isPresent()) {
            userRepository.deleteById(id);
            logger.info("User successfully deleted with ID: {}", id);
            return true;
        } else {
            logger.warn("Cannot find user with ID: {}", id);
            throw new ResourceNotFoundException("Cannot find user with given id!");
        }
    }
}
