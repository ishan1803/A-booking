package com.AuthService.service.implementation;

import com.AuthService.entity.User;
import com.AuthService.repository.UserRepository;
import com.AuthService.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

}
