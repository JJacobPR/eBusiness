package com.ebusiness.ebusiness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "client")
public class Client extends UserEntity {

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    public Client() {}

    public Client(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }

    public Client(LocalDateTime registrationDate, String password, String email, String username, int user_id, String phone, String address) {
        super(registrationDate, password, email, username, user_id);
        this.phone = phone;
        this.address = address;
    }

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