package com.ebusiness.ebusiness.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "package")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private int packageID;

    @ManyToOne
    @JoinColumn(name = "transport_order_id", nullable = false)
    private TransportOrder transportOrder;

    @Column(name = "height", nullable = false)
    private double height;

    @Column(name = "width", nullable = false)
    private double width;

    @Column(name = "depth", nullable = false)
    private double depth;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "is_fragile")
    private boolean isFragile;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Package() {}

    public Package(int packageID, TransportOrder transportOrder, double height, double width, double depth, double weight, boolean isFragile, String comment, LocalDateTime createdAt) {
        this.packageID = packageID;
        this.transportOrder = transportOrder;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.weight = weight;
        this.isFragile = isFragile;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public TransportOrder getTransportOrder() {
        return transportOrder;
    }

    public void setTransportOrder(TransportOrder transportOrder) {
        this.transportOrder = transportOrder;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
