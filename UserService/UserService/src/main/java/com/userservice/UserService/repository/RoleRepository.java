package com.userservice.UserService.repository;


import com.userservice.UserService.entity.Role;
import com.userservice.UserService.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Class - Role Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //Finding User by RoleType
    Optional<Role> findByRole(RoleType roleType);
}