package com.example.brandonderbidge.myapplication;

import java.util.Date;

/**
 * Created by brandonderbidge on 10/11/17.
 */

public class Contract {

    String name;
    String address;
    int apartmentNum;
    int image;
    Date sellBy;
    String city;
    String state;
    int zipCode;
    Double price;
    String maritalStatus;
    String sex;
    String additionalNotes;

    public Contract(String name, Double price, String city, String state, String maritalStatus, String sex) {

        this.name = name;
        this.city = city;
        this.state = state;
        this.price = price;
        this.maritalStatus = maritalStatus;
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getApartmentNum() {
        return apartmentNum;
    }

    public void setApartmentNum(int apartmentNum) {
        this.apartmentNum = apartmentNum;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Date getSellBy() {
        return sellBy;
    }

    public void setSellBy(Date sellBy) {
        this.sellBy = sellBy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getName() {
        return name;
    }


    public int getImage() {
        return image;
    }


}