package com.ebusiness.ebusiness.service;

import com.ebusiness.ebusiness.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> getAllUsers();

    Optional<UserEntity> getUserById(Integer id);

    Optional<UserEntity> getUserByEmail(String email);

    boolean existsByEmail(String email);

    UserEntity createUser(UserEntity user);

    UserEntity updateUser(Integer id, UserEntity updatedUser);

    void deleteUser(Integer id);
}