package com.ebusiness.ebusiness.service.service;

import com.ebusiness.ebusiness.dto.RegisterDriverDto;
import com.ebusiness.ebusiness.entity.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {

    List<Driver> getAllDrivers();

    Optional<Driver> getDriverById(Integer id);

    Optional<Driver> getDriverByEmail(String email);

    boolean existsByEmail(String email);

    Driver createDriver(Driver driver);

    Driver registerDriver(RegisterDriverDto registerDriverDto);

    Driver updateDriver(Integer id, Driver updatedDriver);

    void deleteDriver(Integer id);
}
