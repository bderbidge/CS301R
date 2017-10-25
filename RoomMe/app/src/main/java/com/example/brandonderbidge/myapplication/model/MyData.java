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
            new Contract("a;sldf2jk", "Centennial","Brandon Derbidge", "1000N",  "1", -1, "12/1/2017",   "Provo", "UT", "91205",200.00,"Single", "Male","I need to sell!", "8018575056", "brander81@gmail.com", "12/1/2017"),
            new Contract("a;sldfj4k", "Centennial 2","Brandon Derbidge","1000N", "1", -1, "12/1/2017",  "Provo 2", "UT", "91205",300.00,  "Married", null, "I need to sell!", "8018575056", "brander81@gmail.com", "12/1/2017"),
            new Contract("a;sldfj3k","The Riv", "Brandon Derbidge", "1000N", "1", -1, "12/1/2017",  "Provo", "UT","91205",300.00, "Married", null,"I need to sell!", "8018575056", "brander81@gmail.com", "12/1/2017"),
            new Contract("a;sldfj1k", "Roman Gardens","Brandon Derbidge", "1000N", "1", -1, "12/1/2017","Provo", "UT", "91205", 400.00, "Married", null,"I need to sell!", "8018575056", "brander81@gmail.com", "12/1/2017"),
            new Contract("a;sldfjk5", "King Henry","Brandon Derbidge","1000N", "1", -1, "Provo", "UT","12/1/2017","91205", 200.00,  "Single", "Male","I need to sell!", "8018575056", "brander81@gmail.com", "12/1/2017"),
            new Contract("a;sldfjk6","Brown Stone", "Brandon Derbidge", "1000N","1", -1, "12/1/2017", "Provo", "UT", "91205", 400.00,"Single", "Female","I need to sell!", "8018575056", "brander81@gmail.com", "12/1/2017"),
            new Contract("a;sldfjk", "Centenial", "Brandon Derbidge", "1000N", "1", -1, "12/1/2017", "Provo", "UT", "91205", 200.00,  "Single", "male","I need to sell!", "8018575056", "brander81@gmail.com", "12/1/2017")
    };
    public static Contract[] sellContracts = {
            new Contract("a;sldfjk6","Brown Stone", "Brandon Derbidge", "1000N","1", -1, "12/1/2017", "Provo", "UT", "91205", 400.00,"Single", "Female","I need to sell!", "8018575056", "brander81@gmail.com", "12/1/2017")
    };

}