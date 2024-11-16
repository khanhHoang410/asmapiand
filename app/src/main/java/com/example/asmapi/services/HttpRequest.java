package com.example.asmapi.services;

import static com.example.asmapi.services.APISevice.DOMAIN;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private APISevice requestInterface;

    // hàm tạo
    public HttpRequest(){
        // tạo retrofit
        requestInterface = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APISevice.class);
    }
    public APISevice callAPI(){
        // get Retrofit
        return requestInterface;
    }

}
