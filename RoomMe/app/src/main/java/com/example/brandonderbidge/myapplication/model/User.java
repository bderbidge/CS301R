package com.example.brandonderbidge.myapplication.model;

import java.util.List;

/**
 * Created by brandonderbidge on 10/17/17.
 */

public class User {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private List<String> favoriteContracts;
    private List<String> myContractsToSell;

    public User(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<String> getFavoriteContracts() {
        return favoriteContracts;
    }

    public void setFavoriteContracts(List<String> favoriteContracts) {
        this.favoriteContracts = favoriteContracts;
    }

    public List<String> getMyContractsToSell() {
        return myContractsToSell;
    }

    public void setMyContractsToSell(List<String> myContractsToSell) {
        this.myContractsToSell = myContractsToSell;
    }
}

