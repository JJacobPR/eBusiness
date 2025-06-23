package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.dto.RegisterDriverDto;
import com.ebusiness.ebusiness.entity.Driver;
import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.repository.DriverRepository;
import com.ebusiness.ebusiness.repository.UserRepository;
import com.ebusiness.ebusiness.service.service.DriverService;
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
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, PasswordEncoder passwordEncoder, RoleService roleService, UserService userService) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public Optional<Driver> getDriverById(Integer id) {
        return driverRepository.findById(id);
    }

    @Override
    public Optional<Driver> getDriverByEmail(String email) {
        return driverRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return driverRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return driverRepository.existsByEmail(email);
    }

    @Override
    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver registerDriver(RegisterDriverDto registerDriverDto) {

        if (userService.existsByEmail(registerDriverDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userService.existsByUsername(registerDriverDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Driver driver = new Driver();
        driver.setUsername(registerDriverDto.getUsername());
        driver.setEmail(registerDriverDto.getEmail());
        driver.setPassword(passwordEncoder.encode(registerDriverDto.getPassword()));
        driver.setPhone(registerDriverDto.getPhone());
        driver.setCity(registerDriverDto.getCity());
        driver.setVehicleDetails(registerDriverDto.getVehicleDetails());
        driver.setAvailabilityStatus(false);
        driver.setVerificationStatus(false);
        driver.setBlocked(false);
        driver.setRegistrationDate(LocalDateTime.now());

        Role role = roleService.getRoleByName("DRIVER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        driver.setRoles(Collections.singletonList(role));

        return createDriver(driver);
    }

    @Override
    public Driver updateDriver(Integer id, Driver updatedDriver) {
        return driverRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(updatedDriver.getUsername());
                    existing.setEmail(updatedDriver.getEmail());
                    existing.setPassword(updatedDriver.getPassword());
                    existing.setPhone(updatedDriver.getPhone());
                    existing.setCity(updatedDriver.getCity());
                    existing.setVehicleDetails(updatedDriver.getVehicleDetails());
                    existing.setAvailabilityStatus(updatedDriver.getAvailabilityStatus());
                    existing.setVerificationStatus(updatedDriver.getVerificationStatus());
                    existing.setRegistrationDate(updatedDriver.getRegistrationDate());
                    return driverRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    @Override
    public void deleteDriver(Integer id) {
        driverRepository.deleteById(id);
    }

    @Override
    public void verifyDriver(String email) {

        driverRepository.findByEmail(email).map(existing -> {
            existing.setVerificationStatus(true);
            existing.setAvailabilityStatus(true);
            return driverRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Driver not found"));

    }

    @Override
    public void blockDriver(String email) {

        driverRepository.findByEmail(email).map(existing -> {
            existing.setBlocked(true);
            return driverRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Driver not found"));

    }

    @Override
    public void unblockDriver(String email) {

        driverRepository.findByEmail(email).map(existing -> {
            existing.setBlocked(false);
            return driverRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Driver not found"));

    }


}
