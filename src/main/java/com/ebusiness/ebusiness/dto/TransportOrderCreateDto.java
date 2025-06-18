package com.ebusiness.ebusiness.dto;

import java.util.List;

public class TransportOrderCreateDto {

    private Integer driverId;
    private String originAddress;
    private String destinationAddress;
    private List<PackageCreateDto> packages;

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public List<PackageCreateDto> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageCreateDto> packages) {
        this.packages = packages;
    }
}
