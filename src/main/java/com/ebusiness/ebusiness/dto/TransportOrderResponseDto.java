package com.ebusiness.ebusiness.dto;

import com.ebusiness.ebusiness.entity.TransportOrder;

import java.time.LocalDateTime;
import java.util.List;

public class TransportOrderResponseDto {

    private int orderID;
    private int clientID;
    private int driverID;
    private String originAddress;
    private String destinationAddress;
    private double price;
    private String status;
    private LocalDateTime createdAt;
    private String qrCode;
    private List<PackageResponseDto> packages;

    public TransportOrderResponseDto(TransportOrder transportOrder) {
        this.orderID = transportOrder.getOrderID();
        this.clientID = transportOrder.getClient().getUserID();
        this.driverID = transportOrder.getDriver().getUserID();
        this.originAddress = transportOrder.getOriginAddress();
        this.destinationAddress = transportOrder.getDestinationAddress();
        this.price = transportOrder.getPrice();
        this.status = transportOrder.getStatus();
        this.createdAt = transportOrder.getCreatedAt();
        this.qrCode = transportOrder.getQrCode();
        this.packages = transportOrder.getPackages().stream()
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public List<PackageResponseDto> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageResponseDto> packages) {
        this.packages = packages;
    }
}