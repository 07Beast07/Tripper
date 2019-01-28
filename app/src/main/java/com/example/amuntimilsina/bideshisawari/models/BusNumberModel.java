package com.example.amuntimilsina.bideshisawari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusNumberModel {

    @SerializedName("bus_no")
    @Expose
    String bus_no;

    public BusNumberModel(String bus_no) {
        this.bus_no = bus_no;
    }


    public String getNumber() {
        return bus_no;
    }

    public void setNumber(String bus_no) {
        this.bus_no = bus_no;
    }

}
