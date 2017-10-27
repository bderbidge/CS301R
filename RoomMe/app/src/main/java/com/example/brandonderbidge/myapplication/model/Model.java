package com.example.brandonderbidge.myapplication.model;


import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brandonderbidge on 10/12/17.
 */

public class Model {

    private static Model model = new Model();
    public static Model instance() { return model; };

    private Contract selectedContract;
    private Map<String, Contract> idToContracts = new HashMap<>();
    private User currentUser;
    private Map<String, Contract> allContracts = new HashMap<>();
    private Uri filepath;

    public Uri getFilepath() {
        return filepath;
    }

    public void setFilepath(Uri filepath) {
        this.filepath = filepath;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }



    public Map<String, Contract> getIdToContracts() {
        return idToContracts;
    }



    public Map<String, Contract> getAllContracts() {
        return allContracts;
    }

    public void setAllContracts(Map<String, Contract> allContracts) {
        this.allContracts = allContracts;
    }

    public void setIdToContracts(Map<String, Contract> emailToContracts) {
        this.idToContracts = emailToContracts;
    }



    public Contract getSelectedContract(){
        return selectedContract;
    }

    public void setSelectedContract(Contract selectedContract){
        this.selectedContract = selectedContract;
    }

}
