package com.ebusiness.ebusiness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver extends UserEntity {

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "vehicle_details", columnDefinition = "TEXT")
    private String vehicleDetails;

    @Column(name = "availability_status")
    private Boolean availabilityStatus;

    @Column(name = "verification_status")
    private Boolean verificationStatus;

    @OneToMany(mappedBy = "driver", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<TransportOrder> transportOrders;

    public Driver() {}

    public Driver(String phone, String vehicleDetails, Boolean availabilityStatus, Boolean verificationStatus) {
        this.phone = phone;
        this.vehicleDetails = vehicleDetails;
        this.availabilityStatus = availabilityStatus;
        this.verificationStatus = verificationStatus;
    }

    public Driver(LocalDateTime registrationDate, String password, String email, String username, int user_id, String phone, String vehicleDetails, Boolean availabilityStatus, Boolean verificationStatus) {
        super(registrationDate, password, email, username, user_id);
        this.phone = phone;
        this.vehicleDetails = vehicleDetails;
        this.availabilityStatus = availabilityStatus;
        this.verificationStatus = verificationStatus;
    }

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

    public List<TransportOrder> getTransportOrders() {
        return transportOrders;
    }

    public void setTransportOrders(List<TransportOrder> transportOrders) {
        this.transportOrders = transportOrders;
    }
}
