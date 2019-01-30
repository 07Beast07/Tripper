package com.example.amuntimilsina.bideshisawari.models;

import java.util.ArrayList;

public class AllBusDataFromFirebase {

    String busno;
    Double latitude;
    Double longitude;


    public AllBusDataFromFirebase(String busno, Double latitude, Double longitude) {
        this.busno = busno;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
