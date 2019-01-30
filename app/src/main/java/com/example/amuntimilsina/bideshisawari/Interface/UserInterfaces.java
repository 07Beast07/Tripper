package com.example.amuntimilsina.bideshisawari.Interface;

import com.example.amuntimilsina.bideshisawari.models.LoginModel;
import com.example.amuntimilsina.bideshisawari.models.LoginResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserInterfaces {


    @POST("loginUser.php")
    @Headers("Content-Type:application/json")
    Call<LoginResponseModel> userLogin(@Body LoginModel loginModel);
}
