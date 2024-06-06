package com.userservice.UserService.service;



import com.userservice.UserService.dto.RegisterDTO;
import com.userservice.UserService.dto.UserDTO;
import com.userservice.UserService.entity.UserEntity;

import java.util.List;

//Interface - User Service
public interface UserService {

    //Method - Declaring - Registering a User
    boolean createUser(RegisterDTO registerDTO);

    //Method - Declaring - Getting All User Details
    List<UserEntity> getAllUsers();

    //Method - Declaring - Getting a User by User ID
    UserEntity getUserById(Long id);

    //Method - Declaring - Updating a User by User ID
    boolean updateUserById(Long id, UserDTO userDTO);

    //Method - Declaring - Deleting a User By User ID
    boolean deleteUserById(Long id);
}