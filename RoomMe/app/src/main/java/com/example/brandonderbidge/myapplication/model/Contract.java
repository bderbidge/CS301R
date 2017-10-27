package com.example.brandonderbidge.myapplication.model;

import java.net.URI;

/**
 * Created by brandonderbidge on 10/11/17.
 */

public class Contract {

    private String ID;
    private String apartmentName;
    private String sellerName;
    private String address;
    private String apartmentNum;
    private String filepath;
    private String sellBy;
    private String city;
    private String state;
    private String zipCode;
    private Double price;
    private String maritalStatus;
    private String sex;
    private String additionalNotes;
    private String phoneNumber;
    private String email;
    private String availableDate;


    public Contract(){}

    public Contract(String ID, String apartmentName, String sellerName, String address,
                    String apartmentNum, String filepath, String sellBy, String city, String state,
                    String zipCode, Double price, String maritalStatus, String sex,
                    String additionalNotes, String phoneNumber, String email, String availableDate) {
        this.ID = ID;
        this.apartmentName = apartmentName;
        this.sellerName = sellerName;
        this.address = address;
        this.apartmentNum = apartmentNum;
        this.filepath = filepath;
        this.sellBy = sellBy;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.price = price;
        this.maritalStatus = maritalStatus;
        this.sex = sex;
        this.additionalNotes = additionalNotes;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.availableDate = availableDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartmentNum() {
        return apartmentNum;
    }

    public void setApartmentNum(String apartmentNum) {
        this.apartmentNum = apartmentNum;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getSellBy() {
        return sellBy;
    }

    public void setSellBy(String sellBy) {
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

}