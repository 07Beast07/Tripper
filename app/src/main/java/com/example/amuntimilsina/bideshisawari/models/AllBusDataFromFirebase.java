package com.example.amuntimilsina.bideshisawari.models;

import java.util.ArrayList;

public class AllBusDataFromFirebase {

    String busno;
    String latitude;
    String longitude;

    public AllBusDataFromFirebase(){

    }

    public AllBusDataFromFirebase(String busno, String latitude, String longitude) {
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
