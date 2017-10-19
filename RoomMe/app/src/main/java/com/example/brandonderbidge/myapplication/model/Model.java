package com.example.brandonderbidge.myapplication.model;

import java.util.Map;

/**
 * Created by brandonderbidge on 10/12/17.
 */

public class Model {

    private static Model model = new Model();
    public static Model instance() { return model; };

    private Contract selectedContract;

    public Map<String, Contract> getEmailToContracts() {
        return emailToContracts;
    }

    public void setEmailToContracts(Map<String, Contract> emailToContracts) {
        this.emailToContracts = emailToContracts;
    }

    //Map the ID of the contract to each contract
    private Map<String, Contract> emailToContracts;

    public Contract getSelectedContract(){
        return selectedContract;
    }

    public void setSelectedContract(Contract selectedContract){
        this.selectedContract = selectedContract;
    }

}
