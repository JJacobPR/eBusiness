package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.dto.RegisterDriverDto;
import com.ebusiness.ebusiness.entity.Driver;
import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.repository.DriverRepository;
import com.ebusiness.ebusiness.service.service.DriverService;
import com.ebusiness.ebusiness.service.service.RoleService;
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
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
    public boolean existsByEmail(String email) {
        return driverRepository.existsByEmail(email);
    }

    @Override
    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver registerDriver(RegisterDriverDto dto) {
        if (driverRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Driver driver = new Driver();
        driver.setUsername(dto.getUsername());
        driver.setEmail(dto.getEmail());
        driver.setPassword(passwordEncoder.encode(dto.getPassword()));
        driver.setPhone(dto.getPhone());
        driver.setVehicleDetails(dto.getVehicleDetails());
        driver.setAvailabilityStatus(false);
        driver.setVerificationStatus(false);
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
}
