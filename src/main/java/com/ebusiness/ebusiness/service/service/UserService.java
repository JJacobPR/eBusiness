package com.ebusiness.ebusiness.service.service;

import com.ebusiness.ebusiness.dto.LoginRequestDto;
import com.ebusiness.ebusiness.dto.LoginResponseDto;
import com.ebusiness.ebusiness.dto.RegisterDto;
import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.entity.UserEntity;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity findByEmail(String email);

    List<UserEntity> getAllUsers();

    Optional<UserEntity> getUserById(Integer id);

    Optional<UserEntity> getUserByEmail(String email);

    List<String>getRolesByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    UserEntity createUser(UserEntity user);

    UserEntity registerAdmin(RegisterDto registerDto);

    UserEntity updateUser(Integer id, UserEntity updatedUser);

    void deleteUser(Integer id);

    LoginResponseDto buildResponse(UserEntity user, String token);
}