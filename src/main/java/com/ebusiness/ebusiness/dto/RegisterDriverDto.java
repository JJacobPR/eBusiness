package com.ebusiness.ebusiness.dto;

import com.ebusiness.ebusiness.entity.UserEntity;

public class RegisterDriverDto extends UserEntity {

    private String phone;
    private String vehicleDetails;
    private String city;

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
}
