package com.example.brandonderbidge.myapplication.model;

/**
 * Created by brandonderbidge on 10/11/17.
 */

public class MyData {

    public static String[] nameArray = {"Centennial", "Centennial 2", "The Riv", "Roman Gardens", "King Henry", "Brown Stone"};
    public static Double[] priceArray = {200.00, 300.00, 300.00, 400.00, 200.00, 400.00 };
    public static String[] cityArray = {"Provo", "Provo 2", "Provo", "Provo", "Provo", "Provo"};
    public static String[] stateArray = {"UT", "UT", "UT", "UT", "UT", "UT" };
    public static String[] maritalStatusArray = {"Single", "Married", "Married", "Married", "Single", "Single"};
    public static String[] sexStatusArray ={"Male", null, null, null, "Male", "Female"};
    public static Contract[] contracts = {
            new Contract("Centennial", 200.00, "Provo", "UT", "Single", "Male"),
            new Contract("Centennial 2", 300.00, "Provo 2", "UT", "Married", null),
            new Contract("The Riv", 300.00, "Provo", "UT", "Married", null),
            new Contract("Roman Gardens", 400.00, "Provo", "UT", "Married", null),
            new Contract("King Henry", 200.00, "Provo", "UT", "Single", "Male"),
            new Contract("Brown Stone", 400.00, "Provo", "UT", "Single", "Female")
    };
    public static Contract[] sellContracts = {
            new Contract("James Town", 350.00, "Provo", "UT", "Single", "Male")
    };
}