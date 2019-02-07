package com.example.amuntimilsina.bideshisawari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class AttractionResponseModel {

    @SerializedName("array1")
    @Expose
    private ArrayList<AttractionModel> array1 = new ArrayList<>();

    @SerializedName("array2")
    @Expose
    private ArrayList<AttractionModel2> array2 = new ArrayList<>();



    public AttractionResponseModel(ArrayList<AttractionModel> array1, ArrayList<AttractionModel2> array2) {
        this.array1 = array1;
        this.array2 = array2;
    }

    public ArrayList<AttractionModel> getArray1() {
        return array1;
    }

    public void setArray1(ArrayList<AttractionModel> array1) {
        this.array1 = array1;
    }

    public ArrayList<AttractionModel2> getArray2() {
        return array2;
    }

    public void setArray2(ArrayList<AttractionModel2> array2) {
        this.array2 = array2;
    }
}
