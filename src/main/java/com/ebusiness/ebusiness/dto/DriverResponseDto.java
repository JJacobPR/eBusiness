package com.ebusiness.ebusiness.dto;

import jakarta.persistence.Column;

public class DriverResponseDto extends UserResponseDto {

    private String phone;
    private String vehicleDetails;
    private String city;
    private Boolean availabilityStatus;
    private Boolean verificationStatus;
    private Boolean blocked;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(String vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(Boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Boolean getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(Boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
