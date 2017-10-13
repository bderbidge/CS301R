package com.example.brandonderbidge.myapplication;

/**
 * Created by brandonderbidge on 10/12/17.
 */

public class Model {

    private static Model model = new Model();
    public static Model instance() { return model; };

    private Contract selectedContract;

    public Contract getSelectedContract(){
        return selectedContract;
    }

    public void setSelectedContract(Contract selectedContract){
        this.selectedContract = selectedContract;
    }

}
