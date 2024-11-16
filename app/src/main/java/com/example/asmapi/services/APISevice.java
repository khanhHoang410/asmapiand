package com.example.asmapi.services;


import com.example.asmapi.CarModel;
import com.example.asmapi.model.Distributor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APISevice {

    String DOMAIN = "http://10.24.56.222:3000/";

    @GET("/api/list")
    Call<List<CarModel>> getCars();

    @POST("/api/add_xe")
    Call<Response<CarModel>> addXe(@Body CarModel carmodel);

    @DELETE("api/delete_xe/{id}")
    Call<Response<CarModel>> deleteXebyId(@Path("id") String id);

    @PUT("/api/update_xe/{id}")
    Call<Response<CarModel>> updateXebyId(@Path("id") String id, @Body CarModel carmodel);


}