package com.ebusiness.ebusiness.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transport_order")
public class TransportOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderID;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "origin_address", nullable = false)
    private String originAddress;

    @Column(name = "destination_address", nullable = false)
    private String destinationAddress;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;

    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    @Column(name = "qr_code", columnDefinition = "TEXT")
    private String qrCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "transportOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Package> packages;

    public TransportOrder() {}

    public TransportOrder(int orderID, Client client, Driver driver, String originAddress, String destinationAddress, double price, String status, LocalDateTime pickupTime, LocalDateTime deliveryTime, String qrCode, LocalDateTime createdAt) {
        this.orderID = orderID;
        this.client = client;
        this.driver = driver;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.price = price;
        this.status = status;
        this.pickupTime = pickupTime;
        this.deliveryTime = deliveryTime;
        this.qrCode = qrCode;
        this.createdAt = createdAt;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
}
