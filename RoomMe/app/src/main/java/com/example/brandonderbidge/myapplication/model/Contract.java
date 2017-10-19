package com.example.brandonderbidge.myapplication.model;

/**
 * Created by brandonderbidge on 10/11/17.
 */

public class Contract {

    String ID;
    String apartmentName;
    String sellerName;
    String address;
    int apartmentNum;
    int image;
    String sellBy;
    String city;
    String state;
    int zipCode;
    Double price;
    String maritalStatus;
    String sex;
    String additionalNotes;
    String phoneNumber;
    String email;

    public Contract(String name, Double price, String city, String state, String maritalStatus, String sex) {

        this.apartmentName = name;
        this.city = city;
        this.state = state;
        this.price = price;
        this.maritalStatus = maritalStatus;
        this.sex = sex;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setapartmentName(String name) {
        this.apartmentName = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getApartmentName() {
        return apartmentName;
    }


    public int getImage() {
        return image;
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


}