package com.ebusiness.ebusiness.dto;

public class ClientResponseDto extends LoginResponseDto {
    private String phone;
    private String address;

    public ClientResponseDto() {}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
