package com.ebusiness.ebusiness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ebusiness.ebusiness.entity.Package;

public interface PackageRepository extends JpaRepository<Package, Integer> {
}
