package com.example.amuntimilsina.bideshisawari.Interface;

import com.example.amuntimilsina.bideshisawari.models.LoginModel;
import com.example.amuntimilsina.bideshisawari.models.LoginResponseModel;
import com.example.amuntimilsina.bideshisawari.models.OwnersDataModel;
import com.example.amuntimilsina.bideshisawari.models.OwnersResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OwnersInterface {

    @POST("getOwnersInfo.php")
    @Headers("Content-Type:application/json")
    Call<List<OwnersResponseModel>> ownersDataFetch(@Body OwnersDataModel ownersDataModel);

}
