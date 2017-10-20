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
}
