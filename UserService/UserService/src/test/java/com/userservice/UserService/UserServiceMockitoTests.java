package com.userservice.UserService;

import com.userservice.UserService.dto.RegisterDTO;
import com.userservice.UserService.dto.UserDTO;
import com.userservice.UserService.entity.Role;
import com.userservice.UserService.entity.RoleType;
import com.userservice.UserService.entity.UserEntity;
import com.userservice.UserService.exceptions.BadRequestException;
import com.userservice.UserService.exceptions.ResourceNotFoundException;
import com.userservice.UserService.repository.RoleRepository;
import com.userservice.UserService.repository.UserRepository;
import com.userservice.UserService.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//Class - User Service Mockito Tests
@SpringBootTest(classes = {UserServiceMockitoTests.class})
public class UserServiceMockitoTests {
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    UserServiceImpl userService;

    //Method - Testing - Registering a User
    @Test @Order(1)
    public void test_createUser(){
        RegisterDTO registerDTO=new RegisterDTO();
        registerDTO.setEmail("ishan@email.com");
        registerDTO.setPassword("abc123");
        registerDTO.setName("ishan");
        registerDTO.setRole("CUSTOMER");

        Role role = new Role();
        role.setRole(RoleType.CUSTOMER);

        UserEntity entity= new UserEntity();
        BeanUtils.copyProperties(registerDTO,entity);

        when(userRepository.existsByEmail(registerDTO.getEmail())).thenReturn(false);
        when(roleRepository.findByRole(RoleType.CUSTOMER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(UserEntity.class))).thenReturn(entity);

        assertTrue(userService.createUser(registerDTO));
    }

    //Method - Testing - Registering a User when Email and Password field is Empty
    @Test @Order(2)
    public void test_createUser_EmptyEmailAndPassword() {
        RegisterDTO registerDTO = new RegisterDTO();
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.createUser(registerDTO);
        });

        assertEquals("Email or Password cannot be empty!", exception.getMessage());
    }

    //Method - Testing - Registering a User when Email already Exists
    @Test @Order(3)
    public void test_createUser_EmailAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("ishan@email.com");
        registerDTO.setPassword("abc123");
        registerDTO.setName("Ishan");
        registerDTO.setRole("DEALER");

        when(userRepository.existsByEmail(registerDTO.getEmail())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.createUser(registerDTO);
        });

        assertEquals("Email already exists!", exception.getMessage());
    }

    //Method - Testing - Registering a User when Role not found
    @Test @Order(4)
    public void test_createUser_RoleNotFound() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("ishan@example.com");
        registerDTO.setPassword("abc123");
        registerDTO.setRole("XYZ");
        registerDTO.setName("Ishan");

        when(userRepository.existsByEmail(registerDTO.getEmail())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(registerDTO);
        });

        assertEquals("Invalid role: XYZ", exception.getMessage());
    }

    //Method - Testing -
    @Test @Order(5)
    public void test_getUserById(){

        RegisterDTO user=new RegisterDTO();
        user.setEmail("ishan@email.com");
        user.setPassword("abc123");
        user.setName("ishan");
        user.setRole("CUSTOMER");
        UserEntity entity= new UserEntity();
        BeanUtils.copyProperties(user,entity);
        entity.setId(1);
        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        UserEntity fetchedUser=userService.getUserById(1L);

        assertNotNull(fetchedUser);
        assertEquals(1L,fetchedUser.getId());
        assertEquals("ishan",fetchedUser.getName());
    }

    //Method - Testing - Getting All User Details
    @Test @Order(6)
    public void test_getAllUsers(){
        RegisterDTO user1=new RegisterDTO();
        user1.setEmail("ishan@email.com");
        user1.setPassword("abc123");
        user1.setName("ishan");
        user1.setRole("CUSTOMER");
        UserEntity entity1= new UserEntity();
        BeanUtils.copyProperties(user1,entity1);
        List<UserEntity> users=new ArrayList<UserEntity>();
        users.add(entity1);
        users.add(entity1);
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(2,userService.getAllUsers().size());
    }

    //Method - Testing - Getting a User by User ID
    @Test @Order(7)
    public void test_updateUserById(){
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Shauryan");
        userDTO.setEmail("shauryan@email.com");

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setName("Anoop");
        existingUser.setEmail("anoop@email.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(existingUser);

        assertTrue(userService.updateUserById(userId, userDTO));

        assertEquals(userDTO.getName(), existingUser.getName());
        assertEquals(userDTO.getEmail(), existingUser.getEmail());

    }

    //Method - Testing - Updating a User by User ID when User not found
    @Test @Order(8)
    public void test_updateUserById_UserNotFound(){
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUserById(userId, userDTO);
        });

        assertEquals("Cannot find user with given id!", exception.getMessage());
    }

    //Method - Testing - Updating a User by User ID
    @Test @Order(9)
    public void test_updateUserById_NullPointerException(){
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenThrow(NullPointerException.class);

        assertFalse(userService.updateUserById(userId, userDTO));
    }

    //Method - Testing - Deleting a User By User ID
    @Test @Order(10)
    public void test_deleteUserById(){
        Long userId = 1L;
        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        assertTrue(userService.deleteUserById(userId));

        verify(roleRepository, times(1)).deleteById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    //Method - Testing - Deleting a User By User ID when use not found
    @Test @Order(11)
    public void test_deleteUserById_userNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUserById(userId);
        });

        assertEquals("Cannot find user with given id!", exception.getMessage());

        verifyNoInteractions(roleRepository);
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    //Method - Testing - Registering a user when saving failed
    @Test @Order(12)
    public void test_createUser_SaveUserFailed() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("ishan@email.com");
        registerDTO.setPassword("abc123");
        registerDTO.setRole("CUSTOMER");
        registerDTO.setName("Ishan");

        Role role = new Role();
        role.setRole(RoleType.CUSTOMER);

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(registerDTO, user);

        when(userRepository.existsByEmail(registerDTO.getEmail())).thenReturn(false);
        when(roleRepository.findByRole(RoleType.CUSTOMER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(UserEntity.class))).thenThrow(NullPointerException.class);

        assertFalse(userService.createUser(registerDTO));
    }
}