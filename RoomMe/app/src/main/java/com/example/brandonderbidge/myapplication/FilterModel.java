package com.example.brandonderbidge.myapplication;

/**
 * Created by justinbrunner on 10/12/17.
 */

public class FilterModel {
    private static FilterModel instance;

    private Double priceLow;
    private Double priceHigh;
    private String sex;
    private String maritalStatus;

    private FilterModel() {}

    public static FilterModel getInstance() {
        if (instance == null) {
            instance = new FilterModel();
        }

        return instance;
    }

    public Double getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(Double priceLow) {
        this.priceLow = priceLow;
    }

    public Double getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(Double priceHigh) {
        this.priceHigh = priceHigh;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
