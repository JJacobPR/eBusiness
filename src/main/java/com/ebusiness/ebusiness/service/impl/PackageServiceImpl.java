package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.entity.Package;
import com.ebusiness.ebusiness.repository.PackageRepository;
import com.ebusiness.ebusiness.service.service.PackageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    @Override
    public Optional<Package> getPackageById(Integer id) {
        return packageRepository.findById(id);
    }

    @Override
    public Package createPackage(Package pkg) {
        return packageRepository.save(pkg);
    }

    @Override
    public Package updatePackage(Integer id, Package updatedPackage) {
        return packageRepository.findById(id).map(pkg -> {
            pkg.setTransportOrder(updatedPackage.getTransportOrder());
            pkg.setHeight(updatedPackage.getHeight());
            pkg.setWidth(updatedPackage.getWidth());
            pkg.setDepth(updatedPackage.getDepth());
            pkg.setWeight(updatedPackage.getWeight());
            pkg.setFragile(updatedPackage.isFragile());
            pkg.setComment(updatedPackage.getComment());
            pkg.setCreatedAt(updatedPackage.getCreatedAt());
            return packageRepository.save(pkg);
        }).orElseGet(() -> {
            updatedPackage.setPackageID(id);
            return packageRepository.save(updatedPackage);
        });
    }

    @Override
    public void deletePackage(Integer id) {
        packageRepository.deleteById(id);
    }
}
