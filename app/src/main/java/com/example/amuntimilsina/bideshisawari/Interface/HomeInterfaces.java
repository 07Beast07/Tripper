package com.example.amuntimilsina.bideshisawari.Interface;

import com.example.amuntimilsina.bideshisawari.models.AttractionResponseModel;
import com.example.amuntimilsina.bideshisawari.models.LoginModel;
import com.example.amuntimilsina.bideshisawari.models.LoginResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HomeInterfaces {

    @POST("select_temples.php")
    @Headers("Content-Type:application/json")
    Call<AttractionResponseModel> attractionInterface();
    @POST("select_bank.php")
    @Headers("Content-Type:application/json")
    Call<AttractionResponseModel> parksInterface();
    @POST("select_resturant.php")
    @Headers("Content-Type:application/json")
    Call<AttractionResponseModel> ResturantsInterface();
    @POST("select_hotel.php")
    @Headers("Content-Type:application/json")
    Call<AttractionResponseModel> ShoppingInterface();
    @POST("select_agency.php")
    @Headers("Content-Type:application/json")
    Call<AttractionResponseModel> agencyInterface();

}
