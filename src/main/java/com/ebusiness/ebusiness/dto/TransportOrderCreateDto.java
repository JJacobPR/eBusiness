package com.ebusiness.ebusiness.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TransportOrderCreateDto {

    private String originAddress;
    private String destinationAddress;
    private LocalDateTime pickupTime;
    private LocalDateTime unloadTime;
    private boolean helpUnload;
    private List<PackageCreateDto> packages;

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

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDateTime getUnloadTime() {
        return unloadTime;
    }

    public void setUnloadTime(LocalDateTime unloadTime) {
        this.unloadTime = unloadTime;
    }

    public boolean isHelpUnload() {
        return helpUnload;
    }

    public void setHelpUnload(boolean helpUnload) {
        this.helpUnload = helpUnload;
    }

    public List<PackageCreateDto> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageCreateDto> packages) {
        this.packages = packages;
    }
}
