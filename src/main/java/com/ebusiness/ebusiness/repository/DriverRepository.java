package com.ebusiness.ebusiness.repository;

import com.ebusiness.ebusiness.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Optional<Driver> findByEmail(String email);
    Boolean existsByEmail(String email);
}
