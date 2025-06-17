package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.dto.RegisterDto;
import com.ebusiness.ebusiness.entity.UserEntity;
import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.repository.UserRepository;
import com.ebusiness.ebusiness.service.service.RoleService;
import com.ebusiness.ebusiness.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity registerAdmin(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        UserEntity admin = new UserEntity();
        admin.setUsername(registerDto.getUsername());
        admin.setEmail(registerDto.getEmail());
        admin.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        admin.setRegistrationDate(LocalDateTime.now());

        Role role = roleService.getRoleByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        admin.setRoles(Collections.singletonList(role));

        return createUser(admin);
    }

    @Override
    public UserEntity updateUser(Integer id, UserEntity updatedUser) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setEmail(updatedUser.getEmail());
                    existing.setPassword(updatedUser.getPassword());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
