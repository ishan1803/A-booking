package com.userservice.UserService.controller;


import com.userservice.UserService.dto.RegisterDTO;
import com.userservice.UserService.dto.UserDTO;
import com.userservice.UserService.entity.UserEntity;
import com.userservice.UserService.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Class - User Controller
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    //Method - Mapping - Simple Test
    @GetMapping("/test")
    public String test(){
        return "Hello this is test";
    }

    //Method - Mapping - Registering a User
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        boolean saveUserResult=userService.createUser(registerDTO);
        if(saveUserResult){
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("An error occurred while processing your request. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Method - Mapping - Getting All User Details
    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntity> users=userService.getAllUsers();
        if(users.isEmpty())return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    //Method - Mapping - Getting a User by User ID for Customer
    @GetMapping("forCustomer/{id}")
    public ResponseEntity<UserEntity> getUserByIdforCustomer(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //Method - Mapping - Getting a User by User ID for Expert
    @GetMapping("forExpert/{id}")
    public ResponseEntity<UserEntity> getUserByIdforExpert(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //Method - Mapping - Getting a User by User ID for Admin
    @GetMapping("forAdmin/{id}")
    public ResponseEntity<UserEntity> getUserByIdforAdmin(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //Method - Mapping - Updating a User by User ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        boolean updateUserResult = userService.updateUserById(id, userDTO);

        if(!updateUserResult) {
            return new ResponseEntity<>("Could not process your request, please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
    }

    //Method - Mapping - Deleting a User By User ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        boolean deleteUserResult = userService.deleteUserById(id);

        if(!deleteUserResult) {
            return new ResponseEntity<>("Could not process your request, please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("User with ID " + id + " has been deleted successfully.", HttpStatus.OK);
    }

}