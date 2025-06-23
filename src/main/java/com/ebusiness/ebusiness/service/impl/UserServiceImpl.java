package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.dto.*;
import com.ebusiness.ebusiness.entity.Client;
import com.ebusiness.ebusiness.entity.Driver;
import com.ebusiness.ebusiness.entity.UserEntity;
import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.repository.ClientRepository;
import com.ebusiness.ebusiness.repository.DriverRepository;
import com.ebusiness.ebusiness.repository.UserRepository;
import com.ebusiness.ebusiness.service.service.RoleService;
import com.ebusiness.ebusiness.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final DriverRepository driverRepository;
    private final ClientRepository  clientRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, DriverRepository driverRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.driverRepository = driverRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.getUserEntityByEmail(email);
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
    public List<String> getRolesByEmail(String email) {
        return userRepository.findRolesByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {return userRepository.existsByUsername(username);}

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity registerAdmin(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserEntity admin = new UserEntity();
        admin.setUsername(registerDto.getUsername());
        admin.setEmail(registerDto.getEmail());
        admin.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        admin.setRegistrationDate(LocalDateTime.now());

        List<Role> allRoles = roleService.getAllRoles();
        admin.setRoles(allRoles);

       return userRepository.save(admin);
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

    @Override
    public LoginResponseDto buildResponse(UserEntity user, String token) {
        List<String> roles = getRolesByEmail(user.getEmail());

        if (roles.contains("ADMIN")) {
            return buildAdminResponse(user, token, roles);
        } else if (roles.contains("CLIENT")) {
            return buildClientResponse(user, token, roles);
        } else if (roles.contains("DRIVER")) {
            return buildDriverResponse(user, token, roles);
        }

        throw new IllegalStateException("Unsupported role");
    }

    @Override
    public LoginResponseDto buildResponseWithoutToken(UserEntity user) {
        List<String> roles = getRolesByEmail(user.getEmail());

        if (roles.contains("ADMIN")) {
            return buildAdminResponse(user, null, roles);  // null token
        } else if (roles.contains("CLIENT")) {
            return buildClientResponse(user, null, roles);
        } else if (roles.contains("DRIVER")) {
            return buildDriverResponse(user, null, roles);
        }

        throw new IllegalStateException("Unsupported role");
    }


    private LoginResponseDto buildAdminResponse(UserEntity user, String token, List<String> roles) {
        LoginResponseDto dto = new LoginResponseDto();
        fillCommonUserFields(dto, user, token, roles);
        return dto;
    }

    private ClientLoginResponseDto buildClientResponse(UserEntity user, String token, List<String> roles) {
        Client client = clientRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));

        ClientLoginResponseDto dto = new ClientLoginResponseDto();
        fillCommonUserFields(dto, user, token, roles);
        dto.setPhone(client.getPhone());
        dto.setAddress(client.getAddress());
        return dto;
    }

    private DriverLoginResponseDto buildDriverResponse(UserEntity user, String token, List<String> roles) {
        Driver driver = driverRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Driver not found"));

        DriverLoginResponseDto dto = new DriverLoginResponseDto();
        fillCommonUserFields(dto, user, token, roles);
        dto.setPhone(driver.getPhone());
        dto.setCity(driver.getCity());
        dto.setAvailability_status(driver.getAvailabilityStatus());
        dto.setVehicleDetails(driver.getVehicleDetails());
        return dto;
    }

    private void fillCommonUserFields(LoginResponseDto dto, UserEntity user, String token, List<String> roles) {
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setUser_id(user.getUserID());
        dto.setRegistration_date(user.getRegistrationDate());
        dto.setToken(token);

        // Prioritize ADMIN role if present
        String role = roles.contains("ADMIN") ? "ADMIN" : roles.getFirst();
        dto.setRole(role);
    }
}
