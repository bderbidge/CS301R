package com.example.brandonderbidge.myapplication.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brandonderbidge on 10/12/17.
 */

public class Model {

    private static Model model = new Model();
    public static Model instance() { return model; };

    private Contract selectedContract;

    public Map<String, Contract> getIdToContracts() {
        return idToContracts;
    }

    public void setIdToContracts(Map<String, Contract> emailToContracts) {
        this.idToContracts = emailToContracts;
    }

    //Map the ID of the contract to each contract
    private Map<String, Contract> idToContracts = new HashMap<>();

    public Contract getSelectedContract(){
        return selectedContract;
    }

    public void setSelectedContract(Contract selectedContract){
        this.selectedContract = selectedContract;
    }

}
