package com.example.brandonderbidge.myapplication.buy;

import com.example.brandonderbidge.myapplication.Contract;
import com.example.brandonderbidge.myapplication.R;

/**
 * Created by brandonderbidge on 10/11/17.
 */

public class MyData {

    static String[] nameArray = {"Centennial", "Centennial 2", "The Riv", "Roman Gardens", "King Henry", "Brown Stone"};
    static Double[] priceArray = {200.00, 300.00, 300.00, 400.00, 200.00, 400.00 };
    static String[] cityArray = {"Provo", "Provo 2", "Provo", "Provo", "Provo", "Provo"};
    static String[] stateArray = {"UT", "UT", "UT", "UT", "UT", "UT" };
    static String[] maritalStatusArray = {"Single", "Married", "Married", "Married", "Single", "Single"};
    static String[] sexStatusArray ={"Male", null, null, null, "Male", "Female"};
    static Contract[] contracts = {
            new Contract("Centennial", 200.00, "Provo", "UT", "Single", "Male"),
            new Contract("Centennial 2", 300.00, "Provo 2", "UT", "Married", null),
            new Contract("The Riv", 300.00, "Provo", "UT", "Married", null),
            new Contract("Roman Gardens", 400.00, "Provo", "UT", "Married", null),
            new Contract("King Henry", 200.00, "Provo", "UT", "Single", "Male"),
            new Contract("Brown Stone", 400.00, "Provo", "UT", "Single", "Female")
    };

}