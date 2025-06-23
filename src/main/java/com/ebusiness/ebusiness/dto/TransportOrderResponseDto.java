package com.ebusiness.ebusiness.dto;

import com.ebusiness.ebusiness.config.TransportOrderStatus;
import com.ebusiness.ebusiness.entity.TransportOrder;

import java.time.LocalDateTime;
import java.util.List;

public class TransportOrderResponseDto {

    private int orderID;
    private int clientID;
    private int driverID;
    private String originAddress;
    private String destinationAddress;
    private LocalDateTime pickupTime;
    private LocalDateTime unloadTime;
    private boolean helpUnload;
    private double price;
    private TransportOrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PackageResponseDto> packages;

    public TransportOrderResponseDto(TransportOrder order) {
        this.orderID = order.getOrderID();
        this.clientID = order.getClient().getUserID();
        this.driverID = order.getDriver().getUserID();
        this.originAddress = order.getOriginAddress();
        this.destinationAddress = order.getDestinationAddress();
        this.pickupTime = order.getPickupTime();
        this.unloadTime = order.getDeliveryTime();
        this.helpUnload = order.isHelpUnload();
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
        this.packages = order.getPackages().stream()
                .map(PackageResponseDto::new)
                .toList();
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TransportOrderStatus getStatus() {
        return status;
    }

    public void setStatus(TransportOrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PackageResponseDto> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageResponseDto> packages) {
        this.packages = packages;
    }
}