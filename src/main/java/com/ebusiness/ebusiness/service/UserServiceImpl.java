package com.ebusiness.ebusiness.service;

import com.ebusiness.ebusiness.entity.UserEntity;
import com.ebusiness.ebusiness.repository.UserRepository;
import com.ebusiness.ebusiness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
