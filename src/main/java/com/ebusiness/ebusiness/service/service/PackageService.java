package com.ebusiness.ebusiness.service.service;

import com.ebusiness.ebusiness.entity.Package;

import java.util.List;
import java.util.Optional;

public interface PackageService {
    List<Package> getAllPackages();
    Optional<Package> getPackageById(Integer id);
    Package createPackage(Package pkg);
    Package updatePackage(Integer id, Package updatedPackage);
    void deletePackage(Integer id);
}
