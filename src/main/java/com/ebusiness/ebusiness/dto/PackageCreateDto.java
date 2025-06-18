package com.ebusiness.ebusiness.dto;

public class PackageCreateDto {

    private Integer transportOrderID;
    private double height;
    private double width;
    private double depth;
    private double weight;
    private Boolean isFragile;
    private String comment;

    public Integer getTransportOrderID() {
        return transportOrderID;
    }

    public void setTransportOrderID(Integer transportOrderID) {
        this.transportOrderID = transportOrderID;
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

    public Boolean getIsFragile() {
        return isFragile;
    }

    public void setIsFragile(Boolean isFragile) {
        this.isFragile = isFragile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
