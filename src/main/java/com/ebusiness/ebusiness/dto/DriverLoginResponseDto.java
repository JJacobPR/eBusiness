package com.ebusiness.ebusiness.dto;

public class DriverLoginResponseDto extends LoginResponseDto {
    private String phone;
    private String vehicleDetails;
    private String city;
    private Boolean availability_status;

    public DriverLoginResponseDto() {}

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

    public Boolean getAvailability_status() {
        return availability_status;
    }

    public void setAvailability_status(Boolean availability_status) {
        this.availability_status = availability_status;
    }
}
