package com.example.amuntimilsina.bideshisawari.Interface;

import com.example.amuntimilsina.bideshisawari.models.BusNumberModel;
import com.example.amuntimilsina.bideshisawari.models.BusStationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BusTrackingInterfaces {


    @GET("SelectBusRoutes.php")
    @Headers("Content-Type:application/json")
    Call<List<BusStationModel>> getBusStation();


    @POST("SelectBusNumbers.php")
    @Headers("Content-Type:application/json")
    Call<List<BusNumberModel>> getBusNumberFromBusStation(@Body BusStationModel stationName);


}
