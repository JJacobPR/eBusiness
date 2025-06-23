package com.ebusiness.ebusiness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver extends UserEntity {

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "vehicle_details", columnDefinition = "TEXT", nullable = false)
    private String vehicleDetails;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name = "availability_status")
    private Boolean availabilityStatus;

    @Column(name = "verification_status")
    private Boolean verificationStatus;

    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    @OneToMany(mappedBy = "driver", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<TransportOrder> transportOrders;

    public Driver() {}

    public Driver(String phone, String vehicleDetails, String city, Boolean availabilityStatus, Boolean verificationStatus) {
        this.phone = phone;
        this.vehicleDetails = vehicleDetails;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public List<TransportOrder> getTransportOrders() {
        return transportOrders;
    }

    public void setTransportOrders(List<TransportOrder> transportOrders) {
        this.transportOrders = transportOrders;
    }
}
