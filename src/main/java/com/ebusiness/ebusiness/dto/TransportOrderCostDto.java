package com.ebusiness.ebusiness.dto;

import java.util.List;

public class TransportOrderCostDto {
    private List<PackageCreateDto> packages;
    private boolean helpUnload;

    public List<PackageCreateDto> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageCreateDto> packages) {
        this.packages = packages;
    }

    public boolean isHelpUnload() {
        return helpUnload;
    }

    public void setHelpUnload(boolean helpUnload) {
        this.helpUnload = helpUnload;
    }
}
