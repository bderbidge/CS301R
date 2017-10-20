package com.example.brandonderbidge.myapplication.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by brandonderbidge on 10/17/17.
 */

public class User {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;


    private String ID;
    private List<Contract> favoriteContracts = new ArrayList<>();
    private List<Contract> myContractsToSell = new ArrayList<>();

    public User(){}

    public User(String ID, String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public List<Contract> getFavoriteContracts() {
        return favoriteContracts;
    }

    public void setFavoriteContracts(List<Contract> favoriteContracts) {
        this.favoriteContracts = favoriteContracts;
    }

    public List<Contract> getMyContractsToSell() {
        return myContractsToSell;
    }

    public void setMyContractsToSell(List<Contract> myContractsToSell) {
        this.myContractsToSell = myContractsToSell;
    }
}

