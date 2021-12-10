package com.example.cst438_project03_group08;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @GET("/users")
    Call<List<User>> getUsers();


    @POST("users/")
    Call<String> checkLogin(@Header("Authorization") String authToken);
}
