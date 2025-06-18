package com.ebusiness.ebusiness.dto;

import com.ebusiness.ebusiness.entity.Package;

import java.time.LocalDateTime;

public class PackageResponseDto {
    private int packageID;
    private double height;
    private double width;
    private double depth;
    private double weight;
    private boolean isFragile;
    private String comment;
    private LocalDateTime createdAt;

    public PackageResponseDto(Package pkg) {
        this.packageID = pkg.getPackageID();
        this.height = pkg.getHeight();
        this.width = pkg.getWidth();
        this.depth = pkg.getDepth();
        this.weight = pkg.getWeight();
        this.isFragile = pkg.isFragile();
        this.comment = pkg.getComment();
        this.createdAt = pkg.getCreatedAt();
    }


    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
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