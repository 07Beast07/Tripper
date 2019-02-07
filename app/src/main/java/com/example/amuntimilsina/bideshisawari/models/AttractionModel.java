package com.example.amuntimilsina.bideshisawari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttractionModel {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("place_id")
    @Expose
    String place_id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("latitude")
    @Expose
    String latitude;

    @SerializedName("longitude")
    @Expose
    String longitude;

    @SerializedName("photo_ref")
    @Expose
    String photo_ref;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoto_ref() {
        return photo_ref;
    }

    public void setPhoto_ref(String photo_ref) {
        this.photo_ref = photo_ref;
    }
}
