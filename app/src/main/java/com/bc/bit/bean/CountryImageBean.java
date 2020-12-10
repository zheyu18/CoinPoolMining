package com.bc.bit.bean;

import java.io.Serializable;

public class CountryImageBean implements Serializable {
    /**
     * countryFlag : https://cdn.jin10.com/assets/img/commons/flag/美国.png
     * countryName : 美国
     * id : 1
     */

    private String countryFlag;
    private String countryName;
    private int id;

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
