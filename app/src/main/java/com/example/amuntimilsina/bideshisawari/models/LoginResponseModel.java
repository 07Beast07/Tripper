package com.example.amuntimilsina.bideshisawari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("response")
    @Expose
    private String response;


    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
