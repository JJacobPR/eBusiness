package com.ebusiness.ebusiness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "trackings")
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_id")
    private Integer trackingID;

    @Column(name = "order_id", unique = true, nullable = false)
    private Integer orderID;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "location", nullable = false)
    private String location;

    public Tracking() {}

    public Tracking(Integer orderID, String status, LocalDateTime timestamp, String location) {
        this.orderID = orderID;
        this.status = status;
        this.timestamp = timestamp;
        this.location = location;
    }

    public Integer getTrackingID() {
        return trackingID;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Tracking{" +
                "trackingID=" + trackingID +
                ", orderID=" + orderID +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                ", location='" + location + '\'' +
                '}';
    }
}