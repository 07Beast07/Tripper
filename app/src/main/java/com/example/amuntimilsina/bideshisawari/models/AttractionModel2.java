package com.example.amuntimilsina.bideshisawari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttractionModel2 {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("place_id")
    @Expose
    String place_id;

    @SerializedName("rating")
    @Expose
    String rating;

    @SerializedName("total_users")
    @Expose
    String total_users;

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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTotal_users() {
        return total_users;
    }

    public void setTotal_users(String total_users) {
        this.total_users = total_users;
    }
}
