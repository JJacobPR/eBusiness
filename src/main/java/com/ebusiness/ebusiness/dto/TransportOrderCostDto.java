package com.ebusiness.ebusiness.dto;

import java.util.List;

public class TransportOrderCostDto {
    private List<PackageCreateDto> packages;

    public List<PackageCreateDto> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageCreateDto> packages) {
        this.packages = packages;
    }
}
