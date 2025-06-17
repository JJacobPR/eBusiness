package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.entity.Driver;
import com.ebusiness.ebusiness.repository.DriverRepository;
import com.ebusiness.ebusiness.service.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
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
