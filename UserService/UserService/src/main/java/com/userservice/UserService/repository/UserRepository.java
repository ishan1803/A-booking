package com.userservice.UserService.repository;

import com.userservice.UserService.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Class - User Repository
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    // Checking if the Email Exists
    Boolean existsByEmail(String email);

    // Finding User by Email
    Optional<UserEntity> findByEmail(String email);

}