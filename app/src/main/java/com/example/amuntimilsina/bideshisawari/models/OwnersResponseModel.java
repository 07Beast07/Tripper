package com.example.amuntimilsina.bideshisawari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnersResponseModel {

    @SerializedName("ownername")
    @Expose
    private String ownername;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("place_name")
    @Expose
    private String place_name;




    public OwnersResponseModel(String ownername, String place_name, String email) {
        this.ownername = ownername;
        this.place_name = place_name;
        this.email = email;
    }

    public String getName() {
        return ownername;
    }

    public void setName(String name) {
        this.ownername = name;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
